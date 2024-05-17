from flask import jsonify, Blueprint
from webargs.flaskparser import use_args

from health_monitoring_system_app.errors.validators import validate_json_content_type
from health_monitoring_system_app.models.result import results_schema
from health_monitoring_system_app.models.results_comment import result_comment_schema, \
    result_comment_with_required_id_schema
from health_monitoring_system_app.services.result_service import ResultService

results_blueprint = Blueprint('results_view', __name__, url_prefix='/api')


@results_blueprint.route('/results', methods=['GET'])
def get_results():
    results, pagination = ResultService.get_all_results()
    return jsonify({
        'success': True,
        'data': results,
        'number_of_records': len(results),
        'pagination': pagination
    }), 200


@results_blueprint.route('/results', methods=['POST'])
@validate_json_content_type
@use_args(results_schema, error_status_code=400)
def create_result(args: dict):
    new_result = ResultService.create_result(args)
    return jsonify({
        'success': True,
        'data': new_result}), 201


@results_blueprint.route('/results/<int:result_id>', methods=['GET'])
def get_result_with_comments_by_id(result_id: int):
    result = ResultService.get_result_with_comments_by_id(result_id)
    return jsonify({
        'success': True,
        'data': result}), 200


@results_blueprint.route('/results/<int:result_id>', methods=['DELETE'])
def delete_result_by_id(result_id: int):
    ResultService.delete_result_by_id(result_id)
    return jsonify({
        'success': True,
        'message': f'Result with id {result_id} deleted.'}), 200


@results_blueprint.route('/result-comments', methods=['POST'])
@validate_json_content_type
@use_args(result_comment_schema, error_status_code=400)
def create_result_comment(args: dict):
    new_result_comment = ResultService.create_result_comment(args)
    return jsonify({
        'success': True,
        'data': new_result_comment}), 201


@results_blueprint.route('/result-comments', methods=['PUT'])
@validate_json_content_type
@use_args(result_comment_with_required_id_schema, error_status_code=400)
def update_result_comment(args: dict):
    result = ResultService.update_result_comment(args)
    return jsonify({
        'success': True,
        'data': result}), 200


@results_blueprint.route('/result-comments/<int:result_comment_id>', methods=['GET'])
def get_result_comment_by_id(result_comment_id: int):
    result_comment = ResultService.get_result_comment_by_id(result_comment_id)
    return jsonify({
        'success': True,
        'data': result_comment}), 200


@results_blueprint.route('/result-comments/<int:result_comment_id>', methods=['DELETE'])
def delete_result_comment_by_id(result_comment_id: int):
    ResultService.delete_result_comment_by_id(result_comment_id)
    return jsonify({
        'success': True,
        'message': f'Result comment with id {result_comment_id} deleted.'}), 200