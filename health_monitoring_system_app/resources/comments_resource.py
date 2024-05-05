from flask import jsonify, Blueprint

comments_blueprint = Blueprint('comments_view', __name__, url_prefix='/api')


@comments_blueprint.route('/comments')
def messages():
    response = {
        'id': 1,
        'doctor_id': 1,
    }
    return jsonify(response)


