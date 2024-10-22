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

1.  Build image `docker build -t health-monitoring-app .`
2.  Run container ~~`docker run -p 8080:8080 health-monitoring-app`~~
    `docker run -e DATABASE_URL="" -e DATABASE_USERNAME="" -e DATABASE_PASSWORD="" -p 8080:8080 health-monitoring-app`

Remember to fill the command above with correct credentials. (Put them inside the `""`)