package agh.edu.pl.healthmonitoringsystemapplication.services;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystemapplication.databaseModels.Doctor;
import agh.edu.pl.healthmonitoringsystemapplication.repositories.DoctorRepository;
import agh.edu.pl.healthmonitoringsystemapplication.resources.doctors.models.DoctorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getDoctors(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Doctor> doctorPage = doctorRepository.findAll(pageable);
        return doctorPage.getContent();
    }

    public Doctor createDoctor(DoctorRequest doctorRequest) {
        Doctor doctor = Doctor.builder()
                .name(doctorRequest.getName())
                .surname(doctorRequest.getSurname())
                .email(doctorRequest.getEmail())
                .pesel(doctorRequest.getPesel())
                .pwz(doctorRequest.getPwz())
                .createdAt(LocalDateTime.now())
                .build();
        return doctorRepository.save(doctor);
    }

    public Doctor getDoctorById(Long id){
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + id + " not found"));
    }
}