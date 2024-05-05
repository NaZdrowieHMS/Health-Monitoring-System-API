from flask import jsonify, Blueprint

app_blueprint = Blueprint('app_view', __name__, url_prefix='/api')


@app_blueprint.route("/")
def index():
    return jsonify({
        "Message": "Health-Monitoring-System app up and running successfully"
    }), 200


@app_blueprint.route("/readiness")
def readiness_check():
    pass


@app_blueprint.route("/health ")
def health_check():
    pass
