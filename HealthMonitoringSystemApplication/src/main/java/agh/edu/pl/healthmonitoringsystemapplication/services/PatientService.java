package agh.edu.pl.healthmonitoringsystemapplication.services;


import org.springframework.stereotype.Service;


@Service
public class PatientService {

//    private final PatientRepository patientRepository;
//
//    @Autowired
//    public PatientService(PatientRepository patientRepository) {
//        this.patientRepository = patientRepository;
//    }
//
//    public List<Patient> getPatients(Integer page, Integer size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Patient> patientPage = patientRepository.findAll(pageable);
//        return patientPage.getContent();
//    }
//
//    public Patient createPatient(PatientRequest patientRequest) {
//        Patient patient = Patient.builder()
//                .name(patientRequest.getName())
//                .surname(patientRequest.getSurname())
//                .email(patientRequest.getEmail())
//                .pesel(patientRequest.getPesel())
//                .createdAt(LocalDateTime.now())
//                .lastUpdated(LocalDateTime.now())
//                .build();
//        return patientRepository.save(patient);
//    }
//
//    public Patient getPatientById(Long id){
//        return patientRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
//    }
}
