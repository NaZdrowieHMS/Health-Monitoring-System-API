from datetime import datetime

from flask_sqlalchemy.model import DefaultMeta
from marshmallow import Schema, fields

from health_monitoring_system_app.models import db


class Referral(db.Model, metaclass=DefaultMeta):
    __tablename__ = 'referral'
    id = db.Column(db.Integer, primary_key=True)
    doctor_id = db.Column(db.Integer, db.ForeignKey('doctor.id'), nullable=False)
    patient_id = db.Column(db.Integer, db.ForeignKey('patient.id'), nullable=False)
    comment_id = db.Column(db.Integer, db.ForeignKey('referral_comment.id'), nullable=True)
    number = db.Column(db.String(64), nullable=False)
    test_type = db.Column(db.String(128), nullable=False)
    completed = db.Column(db.Boolean, nullable=False)
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


class ReferralWithComment(db.Model, metaclass=DefaultMeta):
    __tablename__ = 'referral_with_comment'
    referral_id = db.Column(db.Integer, primary_key=True)
    doctor_id = db.Column(db.Integer)
    patient_id = db.Column(db.Integer)
    comment_id = db.Column(db.Integer)
    test_type = db.Column(db.String)
    referral_number = db.Column(db.String)
    completed = db.Column(db.Boolean)
    referral_created_date = db.Column(db.TIMESTAMP)
    referral_modified_date = db.Column(db.TIMESTAMP)
    comment_content = db.Column(db.Text)
    comment_created_date = db.Column(db.TIMESTAMP)
    comment_modified_date = db.Column(db.TIMESTAMP)

    @staticmethod
    def param_validation(param: str, value: str):
        if param in {'comment_created_date', 'comment_modified_date', 'created_date', 'modified_date'} and value is not None:
            try:
                value = datetime.strptime(value, '%Y-%m-%d %H:%M:%S')
            except ValueError:
                value = None
        return value


class BaseReferralWithCommentSchema(Schema):
    test_type = fields.String(required=True)
    comment_content = fields.String(required=False)
    referral_number = fields.String(dump_only=True)


class ReferralWithCommentSchema(BaseReferralWithCommentSchema):
    doctor_id = fields.Integer(required=True)
    patient_id = fields.Integer(required=True)
    referral_id = fields.Integer(dump_only=True)
    comment_id = fields.Integer(dump_only=True)
    completed = fields.Bool(dump_only=True)


class ReferralWithCommentWithRequiredIdSchema(BaseReferralWithCommentSchema):
    referral_id = fields.Integer(required=True)
    completed = fields.Bool(required=True)


referral_with_comment_with_required_id_schema = ReferralWithCommentWithRequiredIdSchema()

referral_with_comment_schema = ReferralWithCommentSchema()
