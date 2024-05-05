from flask import jsonify, Blueprint

results_blueprint = Blueprint('results_view', __name__, url_prefix='/api')


@results_blueprint.route('/results')
def results():
    response = {
        'id': 1,
        'cos': 1,
    }
    return jsonify(response)
