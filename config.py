import os

from dotenv import load_dotenv
from pathlib import Path

base_dir = Path(__file__).resolve().parent
env_path = Path(base_dir, '.env')
load_dotenv(env_path)


class Config:
    SECRET_KEY = os.environ.get('SECRET_KEY')
    SQLALCHEMY_DATABASE_URI = os.environ.get('SQLALCHEMY_DATABASE_URI')
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    DEFAULT_PER_PAGE = 5
    DEFAULT_PAGE = 1


