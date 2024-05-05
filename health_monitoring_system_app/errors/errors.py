from flask import Response, jsonify
from health_monitoring_system_app.repositories import db
from health_monitoring_system_app.errors import error_blueprint


class ErrorResponse:
    def __init__(self, message: str, http_status: int):
        self.payload = {
            'success': False,
            'message': message,
        }
        self.http_status = http_status

    def to_response(self) -> Response:
        response = jsonify(self.payload)
        response.status_code = self.http_status
        return response


@error_blueprint.app_errorhandler(400)
def bad_request_error(error):
    messages = error.data.get('messages', {}).get('json', {})
    return ErrorResponse(messages, 400).to_response()


@error_blueprint.app_errorhandler(409)
def conflict_error(error):
    return ErrorResponse(error.description, 409).to_response()


@error_blueprint.app_errorhandler(404)
def not_found_error(error):
    return ErrorResponse(error.description, 404).to_response()


@error_blueprint.app_errorhandler(415)
def unsupported_media_type_error(error):
    return ErrorResponse(error.description, 415).to_response()


@error_blueprint.app_errorhandler(500)
def internal_server_error(error):
    db.session.rollback()
    return ErrorResponse(error.description, 500).to_response()
