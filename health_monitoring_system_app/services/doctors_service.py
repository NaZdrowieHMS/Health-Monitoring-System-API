from flask import abort
from health_monitoring_system_app.models.doctor import Doctor, DoctorSchema, doctor_schema, \
    doctor_with_required_id_schema
from health_monitoring_system_app.services.utils import apply_sort, apply_filter, apply_pagination
from health_monitoring_system_app.repositories.database_repository import DatabaseRepository


class DoctorsService:
    @staticmethod
    def get_all_doctors():
        query = Doctor.query
        query = apply_sort(Doctor, query)
        query = apply_filter(Doctor, query)
        items, pagination = apply_pagination(query, 'doctors_view.get_doctors')
        patients = DoctorSchema(many=True).dump(items)
        return patients, pagination

    @staticmethod
    def create_doctor(args: dict):
        if Doctor.query.filter(Doctor.pesel == args['pesel']).first():
            abort(409, f'Doctor with pesel {args['pesel']} already exists')
        if Doctor.query.filter(Doctor.pwz == args['pwz']).first():
            abort(409, f'Doctor with pwz {args['pwz']} already exists')
        new_doctor = Doctor(**args)
        DatabaseRepository.create_model(new_doctor)
        return doctor_schema.dump(new_doctor)

    @staticmethod
    def get_doctor_by_id(doctor_id: int):
        doctor = Doctor.query.get_or_404(doctor_id, description=f"Doctor with id {doctor_id} not found.")
        return doctor_schema.dump(doctor)

    @staticmethod
    def delete_doctor_by_id(doctor_id: int):
        doctor = Doctor.query.get_or_404(doctor_id, description=f"Doctor with id {doctor_id} not found.")
        DatabaseRepository.delete_model(doctor)

    @staticmethod
    def update_doctor(args: dict):
        doctor_id = args['id']
        doctor = Doctor.query.get_or_404(doctor_id, description=f"Doctor with id {doctor_id} not found.")
        doctor.name = args['name']
        doctor.surname = args['surname']
        doctor.email = args['email']
        doctor.pesel = args['pesel']
        doctor.pwz = args['pwz']
        DatabaseRepository.save_changes()
        return doctor_with_required_id_schema.dump(doctor)
