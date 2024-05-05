from flask import jsonify, Blueprint

messages_blueprint = Blueprint('messages_view', __name__, url_prefix='/api')


@messages_blueprint.route('/messages')
def messages():
    response = {
        'id': 1,
        'doctor_id': 1,
    }
    return jsonify(response)
