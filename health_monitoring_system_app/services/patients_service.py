from flask import abort
from sqlalchemy import desc

from health_monitoring_system_app.models.ai_prediction import AIPrediction, AIPredictionSchema
from health_monitoring_system_app.models.health_comment import HealthComment, HealthCommentSchema
from health_monitoring_system_app.models.patient import Patient, PatientSchema, patient_schema, patient_with_required_id_schema
from health_monitoring_system_app.models.referral import ReferralWithComment, ReferralWithCommentSchema
from health_monitoring_system_app.services.utils import apply_sort, apply_filter, apply_pagination
from health_monitoring_system_app.repositories.database_repository import DatabaseRepository


class PatientsService:
    @staticmethod
    def get_all_patients():
        query = Patient.query
        query = apply_sort(Patient, query)
        query = apply_filter(Patient, query)
        items, pagination = apply_pagination(query, 'patients_view.get_patients')
        patients = PatientSchema(many=True).dump(items)
        return patients, pagination

    @staticmethod
    def create_patient(args: dict):
        if Patient.query.filter(Patient.pesel == args['pesel']).first():
            abort(409, f'Patient with pesel {args['pesel']} already exists')
        new_patient = Patient(**args)
        DatabaseRepository.create_model(new_patient)
        return patient_schema.dump(new_patient)

    @staticmethod
    def get_patient_by_id(patient_id: int):
        patient = Patient.query.get_or_404(patient_id, description=f"Patient with id {patient_id} not found.")
        return patient_schema.dump(patient)

    @staticmethod
    def delete_patient_by_id(patient_id: int):
        patient = Patient.query.get_or_404(patient_id, description=f"Patient with id {patient_id} not found.")
        DatabaseRepository.delete_model(patient)

    @staticmethod
    def update_patient(args: dict):
        patient_id = args['id']
        patient = Patient.query.get_or_404(patient_id, description=f"Patient with id {patient_id} not found.")
        patient.name = args['name']
        patient.surname = args['surname']
        patient.email = args['email']
        patient.pesel = args['pesel']
        DatabaseRepository.save_changes()
        return patient_with_required_id_schema.dump(patient)

    @staticmethod
    def get_patient_predictions(patient_id: int):
        Patient.query.get_or_404(patient_id, description=f"Patient with id {patient_id} not found.")
        query = AIPrediction.query.filter(AIPrediction.patient_id == patient_id)
        query = query.order_by(desc(AIPrediction.created_date))
        items, pagination = apply_pagination(query, f'patients_view.get_patient_predictions', "patient_id", patient_id)
        predictions = AIPredictionSchema(many=True).dump(items)
        return predictions, pagination

    @staticmethod
    def get_latest_patient_prediction(patient_id: int):
        Patient.query.get_or_404(patient_id, description=f"Patient with id {patient_id} not found.")
        query = AIPrediction.query.filter(AIPrediction.patient_id == patient_id)
        query = query.order_by(desc(AIPrediction.created_date))
        query = query.limit(1)
        prediction = query.first()
        if prediction:
            prediction_data = AIPredictionSchema().dump(prediction)
            return prediction_data
        else:
            abort(404, f'Patient with id {patient_id} has not predictions yet.')

    @staticmethod
    def get_patient_health_comments(patient_id: int):
        Patient.query.get_or_404(patient_id, description=f"Patient with id {patient_id} not found.")
        query = HealthComment.query.filter(HealthComment.patient_id == patient_id)
        query = query.order_by(desc(HealthComment.modified_date))
        items, pagination = apply_pagination(query, f'patients_view.get_patient_health_comments', "patient_id", patient_id)
        health_comments = HealthCommentSchema(many=True).dump(items)
        return health_comments, pagination

    @staticmethod
    def get_patient_referrals(patient_id: int):
        Patient.query.get_or_404(patient_id, description=f"Patient with id {patient_id} not found.")
        query = ReferralWithComment.query.filter(ReferralWithComment.patient_id == patient_id)
        query = query.order_by(ReferralWithComment.completed.desc(), desc(ReferralWithComment.referral_modified_date))
        items, pagination = apply_pagination(query, f'patients_view.get_patient_referrals', "patient_id", patient_id)
        referrals = ReferralWithCommentSchema(many=True).dump(items)
        return referrals, pagination

