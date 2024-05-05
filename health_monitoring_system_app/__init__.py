from flask import Flask, jsonify
from health_monitoring_system_app.repositories import db

from config import Config


def create_app(config_class=Config):
    app = Flask(__name__)
    app.config.from_object(config_class)
    db.init_app(app)

    from health_monitoring_system_app.errors import error_blueprint
    app.register_blueprint(error_blueprint)

    from health_monitoring_system_app.swagger import register_swagger_documentation
    register_swagger_documentation(app)

    from health_monitoring_system_app.resources import register_resource_blueprints
    register_resource_blueprints(app)

    return app
