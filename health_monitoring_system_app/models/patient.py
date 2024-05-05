from flask_sqlalchemy.model import DefaultMeta
from werkzeug.routing import ValidationError

from health_monitoring_system_app.models import db
from marshmallow import Schema, fields, validate, validates


class Patient(db.Model, metaclass=DefaultMeta):
    __tablename__ = 'patient'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(255), nullable=False)
    surname = db.Column(db.String(255), nullable=False)
    email = db.Column(db.String(320), nullable=False)
    pesel = db.Column(db.String(11), nullable=False, unique=True)

    @staticmethod
    def param_validation(param: str, value: str):
        # no additional validation yet
        return value


class BasePatientSchema(Schema):
    name = fields.String(required=True)
    surname = fields.String(required=True)
    email = fields.String(required=True)
    pesel = fields.String(required=True, validate=validate.Length(min=11, max=11))

    @validates('pesel')
    def validate_pesel(self, value: str):
        if len(value) != 11:
            raise ValidationError('Pesel must have 11 digits')


class PatientSchema(BasePatientSchema):
    id = fields.Integer(dump_only=True)


class PatientWithRequiredIdSchema(BasePatientSchema):
    id = fields.Integer(required=True)


patient_with_required_id_schema = PatientWithRequiredIdSchema()

patient_schema = PatientSchema()
