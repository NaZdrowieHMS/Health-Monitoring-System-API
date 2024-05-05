# Health Monitoring System API

This is a backend for Health Monitoring And Diagnosis System Application

## API Description

API contains AI model, conection to database and the service part of the backend needed for the application to work


## Requirements

To install and run the Health Monitoring System backend, you need to have the following:


1. [Docker (version 25.0.3 or higher)](https://www.docker.com/)
2. [AGH VPN connection](https://panel.agh.edu.pl/main.php)
3. Access to the internet for downloading dependencies and VPN 
conncetion


To run the application in an alternative way, you will need  [Python 3.12](https://www.python.org/downloads/release/)

### Run the API

1. Clone this repository to your local machine.

```
git clone https://github.com/NaZdrowieHMS/Health-Monitoring-System-API.git

```

2. Navigate to the directory where the repository is cloned

```
cd Health-Monitoring-System-API
```
3. Ensure you have an AGH VPN connection

4. Open Docker Desktop application

5. Build Docker image with the following command:

```
docker build -t health-monitoring-system-api-image .
```

6. A docker image was created. Build the container with:

```
docker run -p 5000:5000 health-monitoring-system-api-image
```

### API
This command will launch the container and the api will be available at http://localhost:5000/api

### Swagger documentation
Once the server is running, you can access the endpoints using the provided Swagger documentation here: http://localhost:5000/api/docs/

### Alternative way to launch the API
1. Clone this repository to your local machine.

```
git clone https://github.com/NaZdrowieHMS/Health-Monitoring-System-API.git

```

2. Navigate to the directory where the repository is cloned

```
cd Health-Monitoring-System-API
```
3. Install the required dependencies by running the following command:
```
pip install -r requirements.txt
```
4. Ensure you have an AGH VPN connection

5. Run the following command to start the backend server:
```
python -m flask run
```
### API
The api will be available at http://localhost:5000/api

### Swagger documentation
Once the server is running, you can access the endpoints using the provided Swagger documentation here: http://localhost:5000/api/docs/