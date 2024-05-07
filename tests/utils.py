def test_patient(client):
    payload = {
        "name": "Anna",
        "surname": "Test",
        "email": "atest@gmail.com",
        "pesel": "36155552041"
    }
    response = client.post('/api/patients', json=payload)
    return response.get_json()['data']['id']


def test_doctor(client):
    payload = {
        "name": "John",
        "surname": "Doe",
        "email": "johndoe@example.com",
        "pesel": "12345678901",
        "pwz": "1234567"
    }
    response = client.post('/api/doctors', json=payload)
    return response.get_json()['data']['id']


def test_prediction(client):
    doctor_id = test_doctor(client)
    patient_id = test_patient(client)
    payload = {
        "doctor_id": doctor_id,
        "patient_id": patient_id,
        "results_ids": [1, 2, 3]
    }
    response = client.post('/api/predictions', json=payload)
    return response.get_json()['data']['id']