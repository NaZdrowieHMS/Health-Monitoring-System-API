from flask import jsonify, Blueprint
from webargs.flaskparser import use_args

from health_monitoring_system_app.errors.validators import validate_json_content_type
from health_monitoring_system_app.models.patient import patient_schema, patient_with_required_id_schema
from health_monitoring_system_app.services.patients_service import PatientsService

patients_blueprint = Blueprint('patients_view', __name__, url_prefix='/api')


@patients_blueprint.route('/patients', methods=['GET'])
def get_patients():
    patients, pagination = PatientsService.get_all_patients()
    return jsonify({
        'success': True,
        'data': patients,
        'number_of_records': len(patients),
        'pagination': pagination
    }), 200


@patients_blueprint.route('/patients', methods=['POST'])
@validate_json_content_type
@use_args(patient_schema, error_status_code=400)
def create_patient(args: dict):
    new_patient = PatientsService.create_patient(args)
    return jsonify({
        'success': True,
        'data': new_patient}), 201


@patients_blueprint.route('/patients', methods=['PUT'])
@validate_json_content_type
@use_args(patient_with_required_id_schema, error_status_code=400)
def update_patient(args: dict):
    patient = PatientsService.update_patient(args)
    return jsonify({
        'success': True,
        'data': patient}), 200


@patients_blueprint.route('/patients/<int:patient_id>', methods=['GET'])
def get_patient_by_id(patient_id: int):
    patient = PatientsService.get_patient_by_id(patient_id)
    return jsonify({
        'success': True,
        'data': patient}), 200


@patients_blueprint.route('/patients/<int:patient_id>', methods=['DELETE'])
def delete_patient_by_id(patient_id: int):
    PatientsService.delete_patient_by_id(patient_id)
    return jsonify({
        'success': True,
        'message': f'Patient with id {patient_id} deleted.'}), 200
