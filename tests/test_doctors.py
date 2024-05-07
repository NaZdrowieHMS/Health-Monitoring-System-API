from tests.utils import test_doctor


def test_get_doctors_no_records(client):
    response = client.get('/api/doctors')
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['number_of_records'] == 0
    assert response_data['pagination']['current_page'] == '/api/doctors?page=1'
    assert response_data['pagination']['total_pages'] == 0
    assert response_data['pagination']['total_records'] == 0


def test_get_doctor_by_id(client):
    doctor_id = test_doctor(client)
    response = client.get(f'/api/doctors/{doctor_id}')
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['data']['id'] == doctor_id


def test_get_doctor_by_id_error(client):
    response = client.get('/api/doctors/999')
    assert response.status_code == 404
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is False
    assert response_data['message'] == 'Doctor with id 999 not found.'


def test_create_doctor(client):
    response = client.post('/api/doctors', json={
        "name": "John",
        "surname": "Doe",
        "email": "johndoe@example.com",
        "pesel": "12345678901",
        "pwz": "1234567"
    })
    response_data = response.get_json()
    assert response.status_code == 201
    assert response.headers['Content-Type'] == 'application/json'
    assert response_data['success'] is True
    assert response_data['data']['name'] == "John"
    assert response_data['data']['surname'] == "Doe"
    assert response_data['data']['email'] == "johndoe@example.com"
    assert response_data['data']['pesel'] == "12345678901"
    assert response_data['data']['pwz'] == "1234567"
    assert response_data['data']['id'] is not None


def test_create_doctor_error(client):
    payload = {
        "name": "John",
        "surname": "Doe",
        "email": "johndoe@example.com",
        "pesel": "12345678901",
        "pwz": "1234567"
    }
    response = client.post('/api/doctors', json=payload)
    assert response.status_code == 201

    response = client.post('/api/doctors', json=payload)
    assert response.status_code == 409
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is False
    assert response_data['message'] == 'Doctor with pesel 12345678901 already exists'


def test_update_doctor(client):
    post_response = client.post('/api/doctors', json={
        "name": "John",
        "surname": "Doe",
        "email": "johndoe@example.com",
        "pesel": "12345678901",
        "pwz": "1234567"
    })
    doctor_id = post_response.get_json()['data']['id']

    updated_payload = {
        "id": doctor_id,
        "name": "UpdatedJohn",
        "surname": "UpdatedDoe",
        "email": "updatedjohndoe@example.com",
        "pesel": "12345678901",
        "pwz": "1234567"
    }
    response = client.put('/api/doctors', json=updated_payload)
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['data']['name'] == "UpdatedJohn"
    assert response_data['data']['surname'] == "UpdatedDoe"
    assert response_data['data']['email'] == "updatedjohndoe@example.com"
    assert response_data['data']['pesel'] == "12345678901"
    assert response_data['data']['pwz'] == "1234567"
    assert response_data['data']['id'] == doctor_id


def test_update_doctor_error(client):
    payload = {
        "id": 999,
        "name": "Updated John",
        "surname": "Updated Doe",
        "email": "updated_johndoe@example.com",
        "pesel": "12345678901",
        "pwz": "1234567"
    }
    response = client.put('/api/doctors', json=payload)
    assert response.status_code == 404
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is False
    assert response_data['message'] == 'Doctor with id 999 not found.'


def test_delete_doctor_by_id(client):
    doctor_id = test_doctor(client)
    response = client.delete(f'/api/doctors/{doctor_id}')
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['message'] == f'Doctor with id {doctor_id} deleted.'


def test_delete_doctor_by_id_error(client):
    response = client.delete('/api/doctors/999')
    assert response.status_code == 404
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is False
    assert response_data['message'] == 'Doctor with id 999 not found.'
