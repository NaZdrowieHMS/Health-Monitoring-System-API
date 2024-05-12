from flask import jsonify, Blueprint
from webargs.flaskparser import use_args

from health_monitoring_system_app.errors.validators import validate_json_content_type
from health_monitoring_system_app.models.referral import referral_with_comment_schema, \
    referral_with_comment_with_required_id_schema
from health_monitoring_system_app.services.referrals_service import ReferralsService

referrals_blueprint = Blueprint('referrals_view', __name__, url_prefix='/api')


@referrals_blueprint.route('/referrals', methods=['GET'])
def get_referrals():
    referrals, pagination = ReferralsService.get_all_referrals()
    return jsonify({
        'success': True,
        'data': referrals,
        'number_of_records': len(referrals),
        'pagination': pagination
    }), 200


@referrals_blueprint.route('/referrals', methods=['POST'])
@validate_json_content_type
@use_args(referral_with_comment_schema, error_status_code=400)
def create_referral(args: dict):
    new_referral = ReferralsService.create_referral(args)
    return jsonify({
        'success': True,
        'data': new_referral}), 201


@referrals_blueprint.route('/referrals', methods=['PUT'])
@validate_json_content_type
@use_args(referral_with_comment_with_required_id_schema, error_status_code=400)
def update_referral(args: dict):
    referral = ReferralsService.update_referral(args)
    return jsonify({
        'success': True,
        'data': referral}), 200


@referrals_blueprint.route('/referrals/<int:referral_id>', methods=['GET'])
def get_referral_by_id(referral_id: int):
    referral = ReferralsService.get_referral_by_id(referral_id)
    return jsonify({
        'success': True,
        'data': referral}), 200


@referrals_blueprint.route('/referrals/<int:referral_id>', methods=['DELETE'])
def delete_referral_by_id(referral_id: int):
    ReferralsService.delete_referral_by_id(referral_id)
    return jsonify({
        'success': True,
        'message': f'Referral with id {referral_id} deleted.'}), 200


@referrals_blueprint.route('/referrals/<int:referral_id>/comment', methods=['GET'])
def get_referral_comment_by_referral_id(referral_id: int):
    referral_comment = ReferralsService.get_referral_comment_by_referral_id(referral_id)
    return jsonify({
        'success': True,
        'data': referral_comment}), 200


@referrals_blueprint.route('/referrals/<int:referral_id>/comment', methods=['DELETE'])
def delete_referral_comment_by_referral_id(referral_id: int):
    ReferralsService.delete_referral_comment_by_referral_id(referral_id)
    return jsonify({
        'success': True,
        'message': f'Referral comment for referral with id {referral_id} deleted.'}), 200
