package agh.edu.pl.healthmonitoringsystem.domain.services;

import agh.edu.pl.healthmonitoringsystem.domain.components.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exceptions.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.models.response.Doctor;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.DoctorRepository;
import agh.edu.pl.healthmonitoringsystem.domain.models.request.DoctorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    public List<Doctor> getDoctors(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<DoctorEntity> doctors = doctorRepository.findAll(pageable).getContent();

        return doctors.stream()
                .map(modelMapper::mapDoctorEntityToDoctor)
                .collect(Collectors.toList());
    }

    public Doctor createDoctor(DoctorRequest doctorRequest) {
        LocalDateTime now = LocalDateTime.now();
        DoctorEntity doctorEntity = DoctorEntity.builder()
                .name(doctorRequest.getName())
                .surname(doctorRequest.getSurname())
                .email(doctorRequest.getEmail())
                .pesel(doctorRequest.getPesel())
                .pwz(doctorRequest.getPwz())
                .createdDate(now)
                .modifiedDate(now)
                .build();
        DoctorEntity doctorEntitySaved = doctorRepository.save(doctorEntity);

        return modelMapper.mapDoctorEntityToDoctor(doctorEntitySaved);
    }

    public Doctor getDoctorById(Long id){
        DoctorEntity doctorEntity = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + id + " not found"));

        return modelMapper.mapDoctorEntityToDoctor(doctorEntity);
    }
}