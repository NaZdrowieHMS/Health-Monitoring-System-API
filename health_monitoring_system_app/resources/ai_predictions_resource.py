from flask import jsonify, Blueprint
from webargs.flaskparser import use_args

from health_monitoring_system_app.errors.validators import validate_json_content_type
from health_monitoring_system_app.models.ai_prediction import ai_prediction_resource_schema
from health_monitoring_system_app.services.ai_predictions_service import AIPredictionService

ai_prediction_blueprint = Blueprint('ai_prediction_view', __name__, url_prefix='/api')


@ai_prediction_blueprint.route('/predictions')
def get_predictions():
    predictions, pagination = AIPredictionService.get_all_predictions()
    return jsonify({
        'success': True,
        'data': predictions,
        'number_of_records': len(predictions),
        'pagination': pagination
    }), 200


@ai_prediction_blueprint.route('/predictions', methods=['POST'])
@validate_json_content_type
@use_args(ai_prediction_resource_schema, error_status_code=400)
def diagnose_with_ai(args: dict):
    prediction = AIPredictionService.create_prediction(args)
    return jsonify({
        'success': True,
        'data': prediction}), 201


@ai_prediction_blueprint.route('/predictions/<int:prediction_id>', methods=['GET'])
def get_prediction_by_id(prediction_id: int):
    prediction = AIPredictionService.get_prediction_by_id(prediction_id)
    return jsonify({
        'success': True,
        'data': prediction}), 200


@ai_prediction_blueprint.route('/predictions/<int:prediction_id>', methods=['DELETE'])
def delete_prediction_by_id(prediction_id: int):
    AIPredictionService.delete_prediction_by_id(prediction_id)
    return jsonify({
        'success': True,
        'message': f'Prediction with id {prediction_id} deleted.'}), 200
