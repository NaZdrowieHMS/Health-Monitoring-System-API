from datetime import datetime

from flask_sqlalchemy.model import DefaultMeta
from marshmallow import Schema, fields, validates
from werkzeug.routing import ValidationError

from health_monitoring_system_app.models import db
from health_monitoring_system_app.models.results_comment import ResultCommentSchema


class Result(db.Model, metaclass=DefaultMeta):
    __tablename__ = 'result'
    id = db.Column(db.Integer, primary_key=True)
    patient_id = db.Column(db.Integer, db.ForeignKey('patient.id'), nullable=False)
    test_type = db.Column(db.String(128), nullable=False)
    content = db.Column(db.JSON, nullable=False)  #{type: ENUM, data: base64}
    ai_selected = db.Column(db.Boolean, nullable=False)
    viewed = db.Column(db.Boolean, nullable=False)
    created_date = db.Column(db.TIMESTAMP, nullable=False)

    @staticmethod
    def param_validation(param: str, value: str):
        if param == 'created_date':
            try:
                value = datetime.strptime(value, '%Y-%m-%d %H:%M:%S')
            except ValueError:
                value = None
        return value


class ResultBaseSchema(Schema):
    test_type = fields.String(required=True)
    content = fields.Dict(required=True)
    patient_id = fields.Integer(required=True)

    @validates('content')
    def validate_content(self, value):
        if 'type' not in value or 'data' not in value:
            raise ValidationError('Content must have "type" and "data" keys')


class ResultSchema(ResultBaseSchema):
    id = fields.Integer(dump_only=True)
    viewed = fields.Boolean(required=False)
    ai_selected = fields.Boolean(required=False)


class ResultWithCommentsSchema(ResultBaseSchema):
    id = fields.Integer(required=True)
    viewed = fields.Boolean(required=True)
    ai_selected = fields.Boolean(required=True)
    created_date = fields.DateTime(required=True)
    comments = fields.List(fields.Nested(ResultCommentSchema(exclude=['result_id'])), required=True)


results_schema = ResultSchema()

results_with_comments_schema = ResultWithCommentsSchema()
