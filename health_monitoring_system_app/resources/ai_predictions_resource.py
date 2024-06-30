from flask import jsonify, Blueprint, request
from webargs.flaskparser import use_args
import base64
import io
from PIL import Image
import tensorflow as tf
import logging

from health_monitoring_system_app.errors.validators import validate_json_content_type
from health_monitoring_system_app.models.ai_prediction import ai_prediction_resource_schema
from health_monitoring_system_app.models.ai_prediction import ai_prediction_schema_test
from health_monitoring_system_app.services.ai_predictions_service import AIPredictionService

ai_prediction_blueprint = Blueprint('ai_prediction_view', __name__, url_prefix='/api')

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


@ai_prediction_blueprint.route('/predictions')
def get_predictions():
    predictions, pagination = AIPredictionService.get_all_predictions()
    return jsonify({
        'success': True,
        'data': predictions,
        'number_of_records': len(predictions),
        'pagination': pagination
    }), 200


@ai_prediction_blueprint.route('/predictions', methods=['POST'])
@validate_json_content_type
@use_args(ai_prediction_resource_schema, error_status_code=400)
def diagnose_with_ai(args: dict):
    prediction = AIPredictionService.create_prediction(args)
    return jsonify({
        'success': True,
        'data': prediction}), 201


@ai_prediction_blueprint.route('/predictions/<int:prediction_id>', methods=['GET'])
def get_prediction_by_id(prediction_id: int):
    prediction = AIPredictionService.get_prediction_by_id(prediction_id)
    return jsonify({
        'success': True,
        'data': prediction}), 200


@ai_prediction_blueprint.route('/predictions/<int:prediction_id>', methods=['DELETE'])
def delete_prediction_by_id(prediction_id: int):
    AIPredictionService.delete_prediction_by_id(prediction_id)
    return jsonify({
        'success': True,
        'message': f'Prediction with id {prediction_id} deleted.'}), 200

# #TODO remove this function
# @ai_prediction_blueprint.route('/predictions/test/<string:image>', methods=['GET'])
# def get_test_prediction(image: str):
#     return jsonify({
#         'success': True,
#         'message': 'ciap ciap'
#     })

ai_prediction_blueprint = Blueprint('ai_prediction_blueprint', __name__)

# Ścieżka do zapisanego modelu
MODEL_PATH = '/app/health_monitoring_system_app/ai_model/breast_ultrasound_model.h5'

# Załaduj model
model = tf.keras.models.load_model(MODEL_PATH)

def preprocess_image(image):
    image = image.resize((224, 224))
    image = image.convert('RGB')  # Upewnij się, że obraz ma trzy kanały (RGB)
    image_array = [list(image.getdata(band)) for band in range(3)]  # Pobierz dane dla każdego kanału
    image_array = [item / 255.0 for sublist in image_array for item in sublist]  # Normalizacja
    image_array = tf.constant(image_array, shape=(1, 224, 224, 3), dtype=tf.float32)
    return image_array

def decode_base64_image(base64_str):
    image = Image.open(io.BytesIO(base64.decodebytes(bytes(base64_str, "utf-8"))))
    return image

def get_prediction(image):
    processed_image = preprocess_image(image)
    predictions = model.predict(processed_image)
    return predictions

@ai_prediction_blueprint.route('/predictions/test', methods=['POST'])
@validate_json_content_type
@use_args(ai_prediction_schema_test, error_status_code=400)
def test_prediction(args: dict):
    try:
        #data = request.get_json()
        image_base64 = args['image']
        if not image_base64:
            raise ValueError("No image provided")

        image = decode_base64_image(image_base64)
        
        predictions = get_prediction(image)
        predicted_class = tf.argmax(predictions, axis=1).numpy()[0]
        classes = ['benign', 'malignant', 'normal']
        prediction_label = classes[predicted_class]
        
        return jsonify({
            'success': True,
            'prediction': prediction_label,
            'confidence': float(predictions[0][predicted_class])
        })
    except Exception as e:
        return jsonify({
            'success': False,
            'message': str(e)
        }), 400