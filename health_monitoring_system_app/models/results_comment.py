from datetime import datetime

from flask_sqlalchemy.model import DefaultMeta
from marshmallow import Schema, fields

from health_monitoring_system_app.models import db


class ResultComments(db.Model, metaclass=DefaultMeta):
    __tablename__ = 'result_comment'
    id = db.Column(db.Integer, primary_key=True)
    result_id = db.Column(db.Integer, db.ForeignKey('result.id'), nullable=False)
    doctor_id = db.Column(db.Integer, db.ForeignKey('doctor.id'), nullable=False)
    content = db.Column(db.TEXT, nullable=False)
    created_date = db.Column(db.TIMESTAMP, nullable=False)
    modified_date = db.Column(db.TIMESTAMP, nullable=False)

    @staticmethod
    def param_validation(param: str, value: str):
        if param == 'created_date' or param == 'modified_date':
            try:
                value = datetime.strptime(value, '%Y-%m-%d %H:%M:%S')
            except ValueError:
                value = None
        return value


class BaseResultCommentSchema(Schema):
    doctor_id = fields.Integer(required=True)
    content = fields.String(required=True)


class ResultCommentSchema(BaseResultCommentSchema):
    id = fields.Integer(dump_only=True)
    result_id = fields.Integer(required=True)


class ResultCommentWithRequiredIdSchema(BaseResultCommentSchema):
    id = fields.Integer(required=True)
    result_id = fields.Integer(required=False)


result_comment_with_required_id_schema = ResultCommentWithRequiredIdSchema()

result_comment_schema = ResultCommentSchema()
