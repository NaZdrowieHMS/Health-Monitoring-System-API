from flask_sqlalchemy.model import DefaultMeta

from health_monitoring_system_app.models import db
from marshmallow import Schema, fields, validate


class Patient(db.Model, metaclass=DefaultMeta):
    __tablename__ = 'patient'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(255), nullable=False)
    surname = db.Column(db.String(255), nullable=False)
    email = db.Column(db.String(320), nullable=False)
    pesel = db.Column(db.String(11), nullable=False)

    @staticmethod
    def additional_validation(param: str, value: str):
        # no additional validation yet
        return None


class PatientSchema(Schema):
    id = fields.Integer(dump_only=True)
    name = fields.String(required=True)
    surname = fields.String(required=True)
    email = fields.String(required=True)
    pesel = fields.String(required=True, validate=validate.Length(min=11, max=11))


patient_schema = PatientSchema()
