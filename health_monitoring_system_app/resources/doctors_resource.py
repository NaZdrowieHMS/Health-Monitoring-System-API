from flask import jsonify, Blueprint
from webargs.flaskparser import use_args

from health_monitoring_system_app.errors.validators import validate_json_content_type
from health_monitoring_system_app.models.doctor import doctor_schema, doctor_with_required_id_schema
from health_monitoring_system_app.services.doctors_service import DoctorsService

doctors_blueprint = Blueprint('doctors_view', __name__, url_prefix='/api')


@doctors_blueprint.route('/doctors', methods=['GET'])
def get_doctors():
    doctors, pagination = DoctorsService.get_all_doctors()
    return jsonify({
        'success': True,
        'data': doctors,
        'number_of_records': len(doctors),
        'pagination': pagination
    }), 200


@doctors_blueprint.route('/doctors', methods=['POST'])
@validate_json_content_type
@use_args(doctor_schema, error_status_code=400)
def create_doctor(args: dict):
    new_doctor = DoctorsService.create_doctor(args)
    return jsonify({
        'success': True,
        'data': new_doctor}), 201


@doctors_blueprint.route('/doctors', methods=['PUT'])
@validate_json_content_type
@use_args(doctor_with_required_id_schema, error_status_code=400)
def update_doctor(args: dict):
    doctor = DoctorsService.update_doctor(args)
    return jsonify({
        'success': True,
        'data': doctor}), 200


@doctors_blueprint.route('/doctors/<int:doctor_id>', methods=['GET'])
def get_patient_by_id(doctor_id: int):
    doctor = DoctorsService.get_doctor_by_id(doctor_id)
    return jsonify({
        'success': True,
        'data': doctor}), 200


@doctors_blueprint.route('/doctors/<int:doctor_id>', methods=['DELETE'])
def delete_patient_by_id(doctor_id: int):
    DoctorsService.delete_doctor_by_id(doctor_id)
    return jsonify({
        'success': True,
        'message': f'Doctor with id {doctor_id} deleted.'}), 200
