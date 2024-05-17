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


@patients_blueprint.route('/patients/<int:patient_id>/predictions', methods=['GET'])
def get_patient_predictions(patient_id: int):
    predictions, pagination = PatientsService.get_patient_predictions(patient_id)
    return jsonify({
        'success': True,
        'data': predictions,
        'number_of_records': len(predictions),
        'pagination': pagination
    }), 200


@patients_blueprint.route('/patients/<int:patient_id>/predictions/latest', methods=['GET'])
def get_latest_patient_prediction(patient_id: int):
    prediction = PatientsService.get_latest_patient_prediction(patient_id)
    return jsonify({
        'success': True,
        'data': prediction}), 200


@patients_blueprint.route('/patients/<int:patient_id>/health', methods=['GET'])
def get_patient_health_comments(patient_id: int):
    health_comments, pagination = PatientsService.get_patient_health_comments(patient_id)
    return jsonify({
        'success': True,
        'data': health_comments,
        'number_of_records': len(health_comments),
        'pagination': pagination
    }), 200


@patients_blueprint.route('/patients/<int:patient_id>/referrals', methods=['GET'])
def get_patient_referrals(patient_id: int):
    referrals, pagination = PatientsService.get_patient_referrals(patient_id)
    return jsonify({
        'success': True,
        'data': referrals,
        'number_of_records': len(referrals),
        'pagination': pagination
    }), 200


@patients_blueprint.route('/patients/<int:patient_id>/results', methods=['GET'])
def get_patient_results(patient_id: int):
    results, pagination = PatientsService.get_patient_results(patient_id)
    return jsonify({
        'success': True,
        'data': results,
        'number_of_records': len(results),
        'pagination': pagination
    }), 200
