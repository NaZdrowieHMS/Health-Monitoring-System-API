package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.Role;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Doctor;
import agh.edu.pl.healthmonitoringsystem.persistence.UserRepository;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.DoctorRequest;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<Doctor> getDoctors(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedDate").descending());
        List<UserEntity> doctors = userRepository.findAllDoctors(pageable).getContent();

        return doctors.stream()
                .map(modelMapper::mapUserEntityToDoctor)
                .collect(Collectors.toList());
    }

    public Doctor createDoctor(DoctorRequest doctorRequest) {
        LocalDateTime now = LocalDateTime.now();
        UserEntity doctorEntity = UserEntity.builder()
                .role(Role.DOCTOR)
                .name(doctorRequest.getName())
                .surname(doctorRequest.getSurname())
                .email(doctorRequest.getEmail())
                .pesel(doctorRequest.getPesel())
                .pwz(doctorRequest.getPwz())
                .createdDate(now)
                .modifiedDate(now)
                .build();
        UserEntity doctorEntitySaved = userRepository.save(doctorEntity);

        return modelMapper.mapUserEntityToDoctor(doctorEntitySaved);
    }

    public Doctor getDoctorById(Long id){
        UserEntity doctorEntity = userRepository.findDoctorById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + id + " not found"));

        if (!doctorEntity.getRole().equals(Role.DOCTOR)) throw new EntityNotFoundException("Doctor with id " + id + " not found");

        return modelMapper.mapUserEntityToDoctor(doctorEntity);
    }
}