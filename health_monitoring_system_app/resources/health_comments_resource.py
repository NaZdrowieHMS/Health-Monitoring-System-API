from flask import jsonify, Blueprint
from webargs.flaskparser import use_args

from health_monitoring_system_app.errors.validators import validate_json_content_type
from health_monitoring_system_app.models.health_comment import health_comment_schema, health_comment_with_required_id_schema
from health_monitoring_system_app.services.health_comments_service import HealthCommentService

health_blueprint = Blueprint('health_view', __name__, url_prefix='/api')


@health_blueprint.route('/health', methods=['GET'])
def get_health_comments():
    health_comments, pagination = HealthCommentService.get_all_health_comments()
    return jsonify({
        'success': True,
        'data': health_comments,
        'number_of_records': len(health_comments),
        'pagination': pagination
    }), 200


@health_blueprint.route('/health', methods=['POST'])
@validate_json_content_type
@use_args(health_comment_schema, error_status_code=400)
def create_health_comment(args: dict):
    new_health_comment = HealthCommentService.create_health_comment(args)
    return jsonify({
        'success': True,
        'data': new_health_comment}), 201


@health_blueprint.route('/health', methods=['PUT'])
@validate_json_content_type
@use_args(health_comment_with_required_id_schema, error_status_code=400)
def update_health_comment(args: dict):
    health_comment = HealthCommentService.update_health_comment(args)
    return jsonify({
        'success': True,
        'data': health_comment}), 200


@health_blueprint.route('/health/<int:health_id>', methods=['GET'])
def get_health_comment_by_id(health_id: int):
    health_comment = HealthCommentService.get_health_comment_by_id(health_id)
    return jsonify({
        'success': True,
        'data': health_comment}), 200


@health_blueprint.route('/health/<int:health_id>', methods=['DELETE'])
def delete_health_comment_by_id(health_id: int):
    HealthCommentService.delete_health_comment_by_id(health_id)
    return jsonify({
        'success': True,
        'message': f'Health comment with id {health_id} deleted.'}), 200
