from flask_swagger_ui import get_swaggerui_blueprint

SWAGGER_URL = "/api/docs"
API_URL = "/static/swagger.yaml"


def register_swagger_documentation(app):
    swagger_ui_blueprint = get_swaggerui_blueprint(
        SWAGGER_URL,
        API_URL,
        config={'app_name': "Health-Monitoring-System"}
    )
    app.register_blueprint(swagger_ui_blueprint)

