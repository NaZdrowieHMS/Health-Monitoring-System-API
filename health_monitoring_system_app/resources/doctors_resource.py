from flask import jsonify, Blueprint

doctors_blueprint = Blueprint('doctors_view', __name__, url_prefix='/api')


@doctors_blueprint.route('/doctors')
def doctors():
    response = {
        'id': 1,
        'doctor_id': 1,
    }
    return jsonify(response)
