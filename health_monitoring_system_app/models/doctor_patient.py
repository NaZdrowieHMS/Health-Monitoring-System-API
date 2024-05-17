from datetime import datetime

from flask_sqlalchemy.model import DefaultMeta

from health_monitoring_system_app.models import db


class DoctorPatient(db.Model, metaclass=DefaultMeta):
    __tablename__ = 'doctor_patient'
    id = db.Column(db.Integer, primary_key=True)
    doctor_id = db.Column(db.Integer, db.ForeignKey('doctor.id'), nullable=False)
    patient_id = db.Column(db.Integer, db.ForeignKey('patient.id'), nullable=False)
    created_date = db.Column(db.TIMESTAMP, nullable=False)

    @staticmethod
    def param_validation(param: str, value: str):
        if param == 'created_date':
            try:
                value = datetime.strptime(value, '%Y-%m-%d %H:%M:%S')
            except ValueError:
                value = None
        return value