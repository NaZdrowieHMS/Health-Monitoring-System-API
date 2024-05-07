from tests.utils import test_patient, test_doctor, test_prediction


def test_get_predictions(client):
    response = client.get('/api/predictions')
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['number_of_records'] == 0
    assert response_data['pagination']['current_page'] == '/api/predictions?page=1'
    assert response_data['pagination']['total_pages'] == 0
    assert response_data['pagination']['total_records'] == 0


def test_diagnose_with_ai(client):
    patient_id = test_patient(client)
    doctor_id = test_doctor(client)
    payload = {
        "doctor_id": doctor_id,
        "patient_id": patient_id,
        "results_ids": [1, 2, 3]
    }
    response = client.post('/api/predictions', json=payload)
    assert response.status_code == 201
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert 'data' in response_data

