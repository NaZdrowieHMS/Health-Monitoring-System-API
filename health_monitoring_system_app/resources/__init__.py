from .comments_resource import comments_blueprint
from .doctors_resource import doctors_blueprint
from .forms_resource import forms_blueprint
from .messages_resource import messages_blueprint
from .patients_resource import patients_blueprint
from .predictions_resource import predictions_blueprint
from .referrals_resource import referrals_blueprint
from .results_resource import results_blueprint

from .app_resource import app_blueprint


def register_resource_blueprints(app):
    app.register_blueprint(comments_blueprint)
    app.register_blueprint(doctors_blueprint)
    app.register_blueprint(forms_blueprint)
    app.register_blueprint(messages_blueprint)
    app.register_blueprint(patients_blueprint)
    app.register_blueprint(predictions_blueprint)
    app.register_blueprint(referrals_blueprint)
    app.register_blueprint(results_blueprint)

    app.register_blueprint(app_blueprint)
