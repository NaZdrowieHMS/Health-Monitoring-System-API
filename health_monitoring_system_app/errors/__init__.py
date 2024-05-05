from flask import Blueprint

error_blueprint = Blueprint('patients', __name__)

from health_monitoring_system_app.errors import errors
