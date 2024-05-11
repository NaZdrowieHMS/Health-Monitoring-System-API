from tests.utils import test_health_comment, test_doctor, test_patient


def test_get_health_comments_no_records(client):
    response = client.get('/api/health')
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['number_of_records'] == 0
    assert response_data['pagination']['current_page'] == '/api/health?page=1'
    assert response_data['pagination']['total_pages'] == 0
    assert response_data['pagination']['total_records'] == 0


def test_get_health_comment_by_id(client):
    health_comment_id = test_health_comment(client)
    response = client.get(f'/api/health/{health_comment_id}')
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['data']['id'] == health_comment_id


def test_get_health_comment_by_id_error(client):
    response = client.get('/api/health/999')
    assert response.status_code == 404
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is False
    assert response_data['message'] == 'Health comment with id 999 not found.'


def test_create_health_comment(client):
    doctor_id = test_doctor(client)
    patient_id = test_patient(client)
    response = client.post('/api/health', json={
        "doctor_id": doctor_id,
        "patient_id": patient_id,
        "content": "This is a test health comment."
    })
    response_data = response.get_json()
    assert response.status_code == 201
    assert response.headers['Content-Type'] == 'application/json'
    assert response_data['success'] is True
    assert response_data['data']['content'] == "This is a test health comment."
    assert response_data['data']['id'] is not None


def test_update_health_comment(client):
    doctor_id = test_doctor(client)
    patient_id = test_patient(client)
    payload = {
        "doctor_id": doctor_id,
        "patient_id": patient_id,
        "content": "This is a test health comment."
    }
    health_comment_id = client.post('/api/health', json=payload).get_json()['data']['id']
    updated_payload = {
        "id": health_comment_id,
        "doctor_id": doctor_id,
        "content": "This is an updated health comment."
    }
    response = client.put('/api/health', json=updated_payload)
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['data']['content'] == "This is an updated health comment."
    assert response_data['data']['id'] == health_comment_id


def test_update_health_comment_error(client):
    payload = {
        "id": 999,
        "doctor_id": 2,
        "patient_id": 2,
        "content": "This is an updated health comment."
    }
    response = client.put('/api/health', json=payload)
    assert response.status_code == 404
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is False
    assert response_data['message'] == 'Health comment with id 999 not found.'


def test_delete_health_comment_by_id(client):
    health_comment_id = test_health_comment(client)
    response = client.delete(f'/api/health/{health_comment_id}')
    assert response.status_code == 200
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is True
    assert response_data['message'] == f'Health comment with id {health_comment_id} deleted.'


def test_delete_health_comment_by_id_error(client):
    response = client.delete('/api/health/999')
    assert response.status_code == 404
    assert response.headers['Content-Type'] == 'application/json'
    response_data = response.get_json()
    assert response_data['success'] is False
    assert response_data['message'] == 'Health comment with id 999 not found.'
