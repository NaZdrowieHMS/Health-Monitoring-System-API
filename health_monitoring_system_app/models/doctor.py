from flask_sqlalchemy.model import DefaultMeta
from werkzeug.routing import ValidationError

from health_monitoring_system_app.models import db
from marshmallow import Schema, fields, validate, validates


class Doctor(db.Model, metaclass=DefaultMeta):
    __tablename__ = 'doctor'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(255), nullable=False)
    surname = db.Column(db.String(255), nullable=False)
    email = db.Column(db.String(320), nullable=False)
    pesel = db.Column(db.String(11), nullable=False, unique=True)
    pwz = db.Column(db.String(7), nullable=False, unique=True)

    @staticmethod
    def param_validation(param: str, value: str):
        # no additional validation yet
        return value


class BaseDoctorSchema(Schema):
    name = fields.String(required=True)
    surname = fields.String(required=True)
    email = fields.String(required=True)
    pesel = fields.String(required=True, validate=validate.Length(min=11, max=11))
    pwz = fields.String(required=True, validate=validate.Length(min=7, max=7))

    @validates('pesel')
    def validate_pesel(self, value: str):
        if len(value) != 11:
            raise ValidationError('Pesel must have 11 digits')

    @validates('pwz')
    def validate_pwz(self, value: str):
        if len(value) != 7:
            raise ValidationError('PWZ number must have 7 digits')


class DoctorSchema(BaseDoctorSchema):
    id = fields.Integer(dump_only=True)


class DoctorWithRequiredIdSchema(BaseDoctorSchema):
    id = fields.Integer(required=True)


doctor_with_required_id_schema = DoctorWithRequiredIdSchema()

doctor_schema = DoctorSchema()
