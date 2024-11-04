# Health Monitoring System Application

### How to run

1. Add to environment variables in project configuration (during run Spring boot application):
```
DATABASE_URL= 
DATABASE_USERNAME=
DATABASE_PASSWORD=
```
secrets (url, username and password) are available in supabase -> ask Szymon or Karolina

### Run Docker
To build Docker image locally
1.  Build image with your GitHub credentials and token:
```
docker build --build-arg GITHUB_USERNAME=username --build-arg GITHUB_TOKEN=token -t health-monitoring-app .
```
2.  Run container with secrets:
```
docker run -e DATABASE_URL="" -e DATABASE_USERNAME="" -e DATABASE_PASSWORD="" -p 8080:8080 health-monitoring-app
```
Remember to fill the command above with correct credentials. (Put them inside the `""`)

For deployment, notify Szymon on updates:

Our deployment branch is: **deploy**

