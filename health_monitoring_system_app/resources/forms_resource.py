from flask import jsonify, Blueprint

forms_blueprint = Blueprint('forms_view', __name__, url_prefix='/api')


@forms_blueprint.route('/forms')
def messages():
    response = {
        'id': 1,
        'doctor_id': 1,
    }
    return jsonify(response)
