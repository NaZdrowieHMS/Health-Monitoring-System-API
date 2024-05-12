from tests.utils import test_patient


def test_get_patients_no_records(client):
    response = client.get('/api/patients')
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['number_of_records'] == 0
    assert response_data['pagination']['current_page'] == '/api/patients?page=1'
    assert response_data['pagination']['total_pages'] == 0
    assert response_data['pagination']['total_records'] == 0


def test_get_patient_by_id(client):
    patient_id = test_patient(client)

    response = client.get(f'/api/patients/{patient_id}')
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['data']['id'] == patient_id


def test_get_patient_by_id_error(client):
    response = client.get('/api/patients/999')
    assert response.status_code == 404
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is False
    assert response_data['message'] == 'Patient with id 999 not found.'


def test_create_patient(client):
    response = client.post('/api/patients', json={
        "name": "Anna",
        "surname": "Test",
        "email": "atest@gmail.com",
        "pesel": "36155552041"
    })
    response_data = response.get_json()
    assert response.status_code == 201
    assert response.headers['Content-Type'] == 'application/json'
    assert response_data['success'] is True
    assert response_data['data']['name'] == "Anna"
    assert response_data['data']['surname'] == "Test"
    assert response_data['data']['email'] == "atest@gmail.com"
    assert response_data['data']['pesel'] == "36155552041"
    assert response_data['data']['id'] is not None


def test_create_patient_error(client):
    payload = {
        "name": "Anna",
        "surname": "Test",
        "email": "atest@gmail.com",
        "pesel": "36155552041"
    }
    response = client.post('/api/patients', json=payload)
    assert response.status_code == 201

    response = client.post('/api/patients', json=payload)
    assert response.status_code == 409
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is False
    assert response_data['message'] == 'Patient with pesel 36155552041 already exists'


def test_update_patient(client):
    patient_id = test_patient(client)

    updated_payload = {
        "id": patient_id,
        "name": "UpdatedAnna",
        "surname": "UpdatedTest",
        "email": "updated@gmail.com",
        "pesel": "36155552041"
    }
    response = client.put('/api/patients', json=updated_payload)
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['data']['name'] == "UpdatedAnna"
    assert response_data['data']['surname'] == "UpdatedTest"
    assert response_data['data']['email'] == "updated@gmail.com"
    assert response_data['data']['pesel'] == "36155552041"
    assert response_data['data']['id'] == patient_id


def test_update_patient_error(client):
    payload = {
        "id": 999,
        "name": "Updated Anna",
        "surname": "Updated Test",
        "email": "updated_test@gmail.com",
        "pesel": "12345678901"
    }
    response = client.put('/api/patients', json=payload)
    assert response.status_code == 404
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is False
    assert response_data['message'] == 'Patient with id 999 not found.'


def test_delete_patient_by_id(client):
    patient_id = test_patient(client)

    response = client.delete(f'/api/patients/{patient_id}')
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['message'] == f'Patient with id {patient_id} deleted.'


def test_delete_patient_by_id_error(client):
    response = client.delete('/api/patients/999')
    assert response.status_code == 404
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is False
    assert response_data['message'] == 'Patient with id 999 not found.'

