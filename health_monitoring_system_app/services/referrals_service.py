import uuid
from datetime import datetime

from health_monitoring_system_app.models.doctor import Doctor
from health_monitoring_system_app.models.patient import Patient
from health_monitoring_system_app.models.referral import Referral, ReferralWithComment, referral_with_comment_schema, \
    ReferralWithCommentSchema
from health_monitoring_system_app.models.refferal_comment import ReferralComment, referral_comment_schema
from health_monitoring_system_app.services.utils import apply_sort, apply_filter, apply_pagination
from health_monitoring_system_app.repositories.database_repository import DatabaseRepository


def generate_referral_number(patient_id: int, doctor_id: int):
    unique_id = uuid.uuid4().hex
    timestamp = datetime.now().strftime("%Y%m%d%H%M%S")
    referral_number = f"{patient_id}{doctor_id}{timestamp}{unique_id}"
    return referral_number[:20]


class ReferralsService:
    @staticmethod
    def get_all_referrals():
        query = ReferralWithComment.query
        query = apply_sort(ReferralWithComment, query)
        query = apply_filter(ReferralWithComment, query)
        items, pagination = apply_pagination(query, 'referrals_view.get_referrals')
        referrals = ReferralWithCommentSchema(many=True).dump(items)
        return referrals, pagination

    @staticmethod
    def create_referral(args: dict):
        Doctor.query.get_or_404(args['doctor_id'], description=f"Doctor with id {args['doctor_id']} not found.")
        Patient.query.get_or_404(args['patient_id'], description=f"Patient with id {args['patient_id']} not found.")
        args['completed'] = False
        args['number'] = generate_referral_number(args['patient_id'], args['doctor_id'])
        args['created_date'] = datetime.now()
        args['modified_date'] = datetime.now()
        args['comment_id'] = None
        if 'comment_content' in args and args['comment_content']:
            comment_args = {
                'content': args['comment_content'],
                'doctor_id': args['doctor_id'],
                'created_date': args['created_date'],
                'modified_date': args['modified_date']
            }
            new_comment = ReferralComment(**comment_args)
            DatabaseRepository.create_model(new_comment)
            comment_id = referral_comment_schema.dump(new_comment)['id']
            args['comment_id'] = comment_id
        if 'comment_content' in args:
            args.pop('comment_content')
        new_referral = Referral(**args)
        DatabaseRepository.create_model(new_referral)
        DatabaseRepository.flush_changes()
        referral_id = new_referral.id
        referral_with_comment = ReferralWithComment.query.get(referral_id)
        return referral_with_comment_schema.dump(referral_with_comment)

    @staticmethod
    def get_referral_by_id(referral_id: int):
        referral_with_comment = ReferralWithComment.query.get_or_404(referral_id, description=f"Referral with id {referral_id} not found.")
        return referral_with_comment_schema.dump(referral_with_comment)

    @staticmethod
    def delete_referral_by_id(referral_id: int):
        referral = Referral.query.get_or_404(referral_id, description=f"Referral with id {referral_id} not found.")
        if referral.comment_id:
            comment = ReferralComment.query.get_or_404(referral.comment_id, description=f"Referral comment with id {referral.comment_id} not found.")
            DatabaseRepository.delete_model(comment)
        DatabaseRepository.delete_model(referral)

    @staticmethod
    def update_referral(args: dict):
        referral = Referral.query.get_or_404(args['referral_id'], description=f"Referral with id {args['referral_id']} not found.")

        Doctor.query.get_or_404(args['doctor_id'], description=f"Doctor with id {args['doctor_id']} not found.")
        referral.doctor_id = args['doctor_id']

        referral.completed = args['completed']
        referral.modified_date = datetime.now()

        if 'comment_content' in args:
            comment_content = args['comment_content']
            if comment_content:
                if referral.comment_id:
                    comment = ReferralComment.query.get_or_404(referral.comment_id,
                                description=f"Referral comment with id {referral.comment_id} not found.")
                    comment.content = comment_content
                    comment.modified_date = referral.modified_date
                else:
                    comment_args = {
                        'content': comment_content,
                        'doctor_id': referral.doctor_id,
                        'created_date': referral.modified_date,
                        'modified_date': referral.modified_date
                    }
                    new_comment = ReferralComment(**comment_args)
                    DatabaseRepository.create_model(new_comment)
                    referral.comment_id = new_comment.id
        DatabaseRepository.save_changes()
        DatabaseRepository.flush_changes()
        updated_referral = ReferralWithComment.query.get(args['referral_id'])
        return referral_with_comment_schema.dump(updated_referral)

    @staticmethod
    def get_referral_comment_by_referral_id(referral_id: int):
        referral = Referral.query.get_or_404(referral_id, description=f"Referral with id {referral_id} not found.")
        if not referral.comment_id:
            return None
        comment = ReferralComment.query.get_or_404(referral.comment_id,
                                                   description=f"Referral comment with id {referral.comment_id} not found.")
        return referral_comment_schema.dump(comment)

    @staticmethod
    def delete_referral_comment_by_referral_id(referral_id: int):
        referral = Referral.query.get_or_404(referral_id, description=f"Referral with id {referral_id} not found.")
        referral.comment_id = None
        DatabaseRepository.save_changes()
        if referral.comment_id:
            comment = ReferralComment.query.get_or_404(referral.comment_id, description=f"Referral comment with id {referral.comment_id} not found.")
            DatabaseRepository.delete_model(comment)
            referral.comment_id = None
