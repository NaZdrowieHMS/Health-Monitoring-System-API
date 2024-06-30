from datetime import datetime

from flask_sqlalchemy.model import DefaultMeta
from marshmallow import Schema, fields, validates
from werkzeug.routing import ValidationError

from health_monitoring_system_app.models import db


class AIPrediction(db.Model, metaclass=DefaultMeta):
    __tablename__ = 'ai_prediction'
    id = db.Column(db.Integer, primary_key=True)
    doctor_id = db.Column(db.Integer, db.ForeignKey('doctor.id'), nullable=False)
    patient_id = db.Column(db.Integer, db.ForeignKey('patient.id'), nullable=False)
    content = db.Column(db.JSON, nullable=False)  #{type: ENUM, data: base64}
    created_date = db.Column(db.TIMESTAMP, nullable=False)

    @staticmethod
    def param_validation(param: str, value: str):
        if param == 'created_date':
            try:
                value = datetime.strptime(value, '%Y-%m-%d %H:%M:%S')
            except ValueError:
                value = None
        return value


class AIPredictionResourceSchema(Schema):
    doctor_id = fields.Integer(required=True)
    patient_id = fields.Integer(required=True)
    results_ids = fields.List(fields.Integer(), required=True)


class AIPredictionSchema(Schema):
    id = fields.Integer(dump_only=True)
    doctor_id = fields.Integer(required=True)
    patient_id = fields.Integer(required=True)
    content = fields.Dict(required=True)
    created_date = fields.DateTime(required=True)

    @validates('content')
    def validate_content(self, value):
        if 'type' not in value or 'data' not in value:
            raise ValidationError('Content must have "type" and "data" keys')
        
class AIPredictionResourceSchemaTest(Schema):
    image = fields.String(required=True)


ai_prediction_resource_schema = AIPredictionResourceSchema()

ai_prediction_schema = AIPredictionSchema()

ai_prediction_schema_test = AIPredictionResourceSchemaTest()
