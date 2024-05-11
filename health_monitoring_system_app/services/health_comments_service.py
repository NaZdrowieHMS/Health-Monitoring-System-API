from datetime import datetime

from sqlalchemy import desc

from health_monitoring_system_app.models.doctor import Doctor
from health_monitoring_system_app.models.health_comment import HealthComment, HealthCommentSchema
from health_monitoring_system_app.models.health_comment import health_comment_schema, health_comment_with_required_id_schema
from health_monitoring_system_app.models.patient import Patient
from health_monitoring_system_app.repositories.database_repository import DatabaseRepository
from health_monitoring_system_app.services.utils import apply_filter, apply_sort, apply_pagination


class HealthCommentService:
    @staticmethod
    def get_all_health_comments():
        query = HealthComment.query
        query = query.order_by(desc(HealthComment.modified_date))
        query = apply_sort(HealthComment, query)
        query = apply_filter(HealthComment, query)
        items, pagination = apply_pagination(query, 'health_view.get_health_comments')
        health_comments = HealthCommentSchema(many=True).dump(items)
        return health_comments, pagination

    @staticmethod
    def create_health_comment(args: dict):
        Doctor.query.get_or_404(args['doctor_id'], description=f"Doctor with id {args['doctor_id']} not found.")
        Patient.query.get_or_404(args['patient_id'], description=f"Patient with id {args['patient_id']} not found.")
        args['created_date'] = datetime.now()
        args['modified_date'] = datetime.now()
        new_health_comment = HealthComment(**args)
        DatabaseRepository.create_model(new_health_comment)
        return health_comment_schema.dump(new_health_comment)

    @staticmethod
    def update_health_comment(args: dict):
        health_comment_id = args['id']
        health_comment = HealthComment.query.get_or_404(health_comment_id, description=f"Health comment with id {health_comment_id} not found.")
        Doctor.query.get_or_404(args['doctor_id'], description=f"Doctor with id {args['doctor_id']} not found.")
        health_comment.doctor_id = args['doctor_id']
        health_comment.content = args['content']
        health_comment.modified_date = datetime.now()
        DatabaseRepository.save_changes()
        return health_comment_with_required_id_schema.dump(health_comment)

    @staticmethod
    def get_health_comment_by_id(health_id: int):
        health_comment = HealthComment.query.get_or_404(health_id, description=f"Health comment with id {health_id} not found.")
        return health_comment_schema.dump(health_comment)

    @staticmethod
    def delete_health_comment_by_id(health_id: int):
        health_comment = HealthComment.query.get_or_404(health_id, description=f"Health comment with id {health_id} not found.")
        DatabaseRepository.delete_model(health_comment)
