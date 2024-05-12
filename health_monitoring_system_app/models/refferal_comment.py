from datetime import datetime

from flask_sqlalchemy.model import DefaultMeta
from marshmallow import Schema, fields

from health_monitoring_system_app.models import db


class ReferralComment(db.Model, metaclass=DefaultMeta):
    __tablename__ = 'referral_comment'
    id = db.Column(db.Integer, primary_key=True)
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


class BaseReferralCommentSchema(Schema):
    doctor_id = fields.Integer(required=True)
    content = fields.String(required=True)
    created_date = fields.DateTime(required=False)
    modified_date = fields.DateTime(required=False)


class ReferralCommentSchema(BaseReferralCommentSchema):
    id = fields.Integer(dump_only=True)


class ReferralCommentWithRequiredIdSchema(BaseReferralCommentSchema):
    id = fields.Integer(required=True)


referral_comment_with_required_id_schema = ReferralCommentWithRequiredIdSchema()

referral_comment_schema = ReferralCommentSchema()
