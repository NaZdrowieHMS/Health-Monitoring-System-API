FROM python:3.12-slim

WORKDIR /app

COPY requirements.txt .

RUN pip install --no-cache-dir -r requirements.txt

COPY . .

ARG PORT=5000

EXPOSE 5000

CMD ["python", "-m", "flask", "run", "--host=0.0.0.0"]
