package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultForDoctorView;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultWithPatientData;
import agh.edu.pl.healthmonitoringsystem.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithAiSelectedAndViewedProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithPatientDataProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorPatientService {

    private final PatientRepository patientRepository;
    private final ResultRepository resultRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorPatientService(PatientRepository patientRepository,  ResultRepository resultRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.resultRepository = resultRepository;
        this.modelMapper = modelMapper;
    }

    public List<Patient> getPatientsByDoctorId(Long doctorId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<PatientEntity> patients = patientRepository.findPatientsByDoctorId(doctorId, pageRequest).getContent();
        return patients.stream()
                .map(modelMapper::mapPatientEntityToPatient)
                .collect(Collectors.toList());
    }

    public List<ResultForDoctorView> getDoctorPatientResultWithAiSelectedAndViewed(Long doctorId, Long patientId) {
        List<ResultWithAiSelectedAndViewedProjection> results = resultRepository.getDoctorPatientResultWithAiSelectedAndViewed(doctorId, patientId);
        return results.stream()
                .map(modelMapper::mapProjectionToResultForDoctorView)
                .collect(Collectors.toList());
    }

    public List<ResultWithPatientData> getDoctorUnviewedResults(Long doctorId) {
        List<ResultWithPatientDataProjection> results = resultRepository.getDoctorUnviewedResults(doctorId);
        return results.stream()
                .map(modelMapper::mapProjectionToResultWithPatientData)
                .collect(Collectors.toList());
    }
}
