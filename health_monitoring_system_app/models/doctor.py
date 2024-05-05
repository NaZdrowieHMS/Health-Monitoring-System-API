class Doctor:
    def __init__(self, id, name, surname, email, pesel, pwz):
        self.id = id
        self.name = name
        self.surname = surname
        self.email = email
        self.pesel = pesel
        self.pwz = pwz

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'surname': self.surname,
            'email': self.email,
            'pesel': self.pesel,
            'pwz': self.pwz
        }