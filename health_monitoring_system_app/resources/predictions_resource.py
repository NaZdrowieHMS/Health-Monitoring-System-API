from flask import jsonify, Blueprint

predictions_blueprint = Blueprint('predictions_view', __name__, url_prefix='/api')


@predictions_blueprint.route('/predictions')
def predictions():
    response = {
        'id': 1,
        'doctor_id': 1,
    }
    return jsonify(response)
