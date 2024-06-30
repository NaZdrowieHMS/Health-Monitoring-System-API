FROM python:3.12-slim

ARG PORT=5000

ENV PORT=${PORT}

WORKDIR /app

RUN apt-get update && apt-get install -y \
    build-essential \
    libssl-dev \
    libffi-dev \
    python3-dev \
    libjpeg-dev \
    zlib1g-dev \
    libhdf5-dev \
    pkg-config \
    && apt-get clean

COPY requirements.txt .

RUN pip install --upgrade pip setuptools wheel

RUN pip install --no-cache-dir -r requirements.txt

COPY . .

EXPOSE 5000

CMD ["python", "-m", "flask", "run", "--host=0.0.0.0", "--port=5000"]