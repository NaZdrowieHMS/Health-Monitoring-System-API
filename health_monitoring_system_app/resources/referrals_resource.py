from flask import jsonify, Blueprint

referrals_blueprint = Blueprint('referrals_view', __name__, url_prefix='/api')


@referrals_blueprint.route('/referrals')
def referrals():
    response = {
        'id': 1,
        'doctor_id': 1,
    }
    return jsonify(response)
