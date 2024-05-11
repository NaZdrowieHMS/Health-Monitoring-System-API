from datetime import datetime

from health_monitoring_system_app.models.ai_prediction import AIPrediction, AIPredictionResourceSchema, \
    ai_prediction_schema, AIPredictionSchema
from health_monitoring_system_app.models.doctor import Doctor
from health_monitoring_system_app.models.patient import Patient
from health_monitoring_system_app.services.utils import apply_sort, apply_filter, apply_pagination
from health_monitoring_system_app.repositories.database_repository import DatabaseRepository


class AIPredictionService:
    @staticmethod
    def get_all_predictions():
        query = AIPrediction.query
        query = apply_sort(AIPrediction, query)
        query = apply_filter(AIPrediction, query)
        items, pagination = apply_pagination(query, 'ai_prediction_view.get_predictions')
        predictions = AIPredictionSchema(many=True).dump(items)
        return predictions, pagination

    @staticmethod
    def get_prediction_by_id(prediction_id: int):
        prediction = AIPrediction.query.get_or_404(prediction_id,
                                                   description=f"AI Prediction with id {prediction_id} not found.")
        return ai_prediction_schema.dump(prediction)

    @staticmethod
    def delete_prediction_by_id(prediction_id: int):
        prediction = AIPrediction.query.get_or_404(prediction_id, description=f"AI Prediction with id {prediction_id} not found.")
        DatabaseRepository.delete_model(prediction)

    @staticmethod
    def create_prediction(args):
        '''
        TODO: ustalić co ma dostawać model ai i co zwraca, żeby serwis mógł uwtorzyc
        obiekt do zapisania w bazie, zapisać go w bazie i zwrócić na front
        '''
        # new_prediction = Tworzenie nowego obiektu -> najpewniej AIPrediction()
        # DatabaseRepository.create_model(new_prediction) # zapisanie modelu do bazy
        #
        # return ai_prediction_schema.dump(new_prediction) -> zwróci nowy obiekt AIPrediction
        mock_content = {
                "type": "date",
                "data": "MjAyNC0wNS0wNlQxMjozMDo0NQ=="
              }
        Doctor.query.get_or_404(args['doctor_id'], description=f"Doctor with id {args['doctor_id']} not found.")
        Patient.query.get_or_404(args['patient_id'], description=f"Patient with id {args['patient_id']} not found.")
        #TODO pamietac o sprawdzaniu med test results id

        new_prediction_mock = AIPrediction(
            id=1,
            doctor_id=args['doctor_id'],
            patient_id=args['patient_id'],
            content=mock_content,
            created_date=datetime.now()
        )
        # DatabaseRepository.create_model(new_prediction_mock)
        return ai_prediction_schema.dump(new_prediction_mock)
