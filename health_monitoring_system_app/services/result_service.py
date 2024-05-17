from datetime import datetime

from health_monitoring_system_app.models.doctor import Doctor
from health_monitoring_system_app.models.patient import Patient
from health_monitoring_system_app.models.referral import ReferralWithComment
from health_monitoring_system_app.models.result import ResultSchema, Result, results_schema, \
    results_with_comments_schema, ResultWithCommentsSchema
from health_monitoring_system_app.models.results_comment import ResultComments, result_comment_schema, \
    result_comment_with_required_id_schema
from health_monitoring_system_app.services.utils import apply_sort, apply_filter, apply_pagination
from health_monitoring_system_app.repositories.database_repository import DatabaseRepository


class ResultService:
    @staticmethod
    def get_all_results():
        query = Result.query
        query = apply_sort(Result, query)
        query = apply_filter(Result, query)
        items, pagination = apply_pagination(query, 'doctors_view.get_doctors')
        results = ResultSchema(many=True).dump(items)
        return results, pagination

    @staticmethod
    def create_result(args: dict):
        Patient.query.get_or_404(args['patient_id'], description=f"Patient with id {args['patient_id']} not found.")
        args['created_date'] = datetime.now()
        args['viewed'] = False
        args['ai_selected'] = False
        new_result = Result(**args)
        DatabaseRepository.create_model(new_result)
        return results_schema.dump(new_result)

    @staticmethod
    def get_result_with_comments_by_id(result_id: int):
        result = Result.query.get_or_404(result_id, description=f"Result with id {result_id} not found.")
        results_with_comments = {
            'id': result.id,
            'test_type': result.test_type,
            'content': result.content,
            'patient_id': result.patient_id,
            'ai_selected': result.ai_selected,
            'viewed': result.viewed,
            'created_date': result.created_date,
            'comments': ResultComments.query.filter_by(result_id=result_id).all()
        }
        return results_with_comments_schema.dump(results_with_comments)

    @staticmethod
    def delete_result_by_id(result_id: int):
        result = Result.query.get_or_404(result_id, description=f"Result with id {result_id} not found.")
        comments = ResultComments.query.filter_by(result_id=result_id).all()
        for comment in comments:
            DatabaseRepository.delete_model(comment)
        DatabaseRepository.delete_model(result)

    @staticmethod
    def create_result_comment(args):
        Result.query.get_or_404(args['result_id'], description=f"Result with id {args['result_id']} not found.")
        Doctor.query.get_or_404(args['doctor_id'], description=f"Doctor with id {args['doctor_id']} not found.")
        args['created_date'] = datetime.now()
        args['modified_date'] = args['created_date']
        new_result_comment = ResultComments(**args)
        DatabaseRepository.create_model(new_result_comment)
        return result_comment_schema.dump(new_result_comment)

    @staticmethod
    def update_result_comment(args):
        result_comment_id = args['id']
        result_comment = ResultComments.query.get_or_404(result_comment_id,
                                                         description=f"Result comment with id {result_comment_id} not found.")
        Doctor.query.get_or_404(args['doctor_id'], description=f"Doctor with id {args['doctor_id']} not found.")
        result_comment.modified_date = datetime.now()
        result_comment.doctor_id = args['doctor_id']
        result_comment.content = args['content']
        DatabaseRepository.save_changes()
        return result_comment_with_required_id_schema.dump(result_comment)

    @staticmethod
    def get_result_comment_by_id(result_comment_id):
        result_comment = ResultComments.query.get_or_404(result_comment_id,
                                                         description=f"Result comment with id {result_comment_id} not found.")
        return result_comment_schema.dump(result_comment)

    @staticmethod
    def delete_result_comment_by_id(result_comment_id):
        result_comment = ResultComments.query.get_or_404(result_comment_id,
                                                         description=f"Result comment with id {result_comment_id} not found.")
        DatabaseRepository.delete_model(result_comment)
