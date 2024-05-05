from health_monitoring_system_app.repositories import db


class DatabaseRepository:
    @staticmethod
    def save_changes():
        db.session.commit()

    @staticmethod
    def get_all(model: db.Model):
        return model.query.all()

    @staticmethod
    def create_model(new_model: db.Model):
        db.session.add(new_model)
        db.session.commit()

    @staticmethod
    def delete_model(model: db.Model):
        db.session.delete(model)
        db.session.commit()
