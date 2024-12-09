package agh.edu.pl.healthmonitoringsystem.steps;

import agh.edu.pl.healthmonitoringsystem.FunctionalTestExecutor;
import agh.edu.pl.healthmonitoringsystem.domain.model.Role;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.persistence.UserRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.UserEntity;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class HealthCommentSteps {

    @Autowired
    private FunctionalTestExecutor executor;

    @Autowired
    private UserRepository userRepository;

    private ResponseEntity<Comment> response;
    private UserEntity doctor;
    private UserEntity patient;

    @Given("The patient with ID {long} and the doctor with ID {long}")
    public void thePatientWithIdAndTheDoctorWithId(Long patientId, Long doctorId) {
        patient = createUser(patientId, Role.PATIENT, "Anna", "Nowak", "anowak@email.com", "03991723919", null);
        doctor = createUser(doctorId, Role.DOCTOR, "Adam", "Kowalski", "akowal@email.com", "02011023919", "5425740");}

    @When("The doctor adds a comment {string}")
    public void theDoctorAddsAComment(String comment) {
        CommentRequest requestBody = new CommentRequest(doctor.getId(), patient.getId(), comment);
        response = executor.addCommentApiCall(requestBody);
    }

    @Then("The hms api should return status {int}")
    public void theSystemShouldReturnStatus(int status) {
        assertEquals(status, response.getStatusCode().value());
    }

    @And("The health comment should be created properly with {string} content")
    public void theHealthCommentShouldBeCreatedProperlyWithContent(String content) {
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(content, response.getBody().content(), "Comment content mismatch");
        assertEquals(doctor.getId(), response.getBody().doctor().id(), "Doctor ID mismatch");
        assertEquals(doctor.getName(), response.getBody().doctor().name(), "Doctor name mismatch");
        assertEquals(doctor.getSurname(), response.getBody().doctor().surname(), "Doctor surname mismatch");
    }


    private UserEntity createUser(Long id, Role role, String name, String surname, String email, String pesel, String pwz) {
        LocalDateTime now = LocalDateTime.now();
        UserEntity user = new UserEntity(id, role, name, surname, email, pesel, pwz, now, now);
        userRepository.save(user);
        return user;
    }

    @Given("The patient with ID {long} has existing health comments added by multiple doctors")
    public void thePatientWithIdhasExistingHealthComments(Long patientId) {
        patient = createUser(patientId, Role.PATIENT, "Anna", "Nowak", "anowak@email.com", "03991723919", null);
    }

    @When("The doctor requests for the patient's health comments")
    public void theDoctorRequestsForThePatientsHealthComments() {
    }

    @Then("The hms api should return all health comments associated with patient ID {int}")
    public void theHmsApiShouldReturnAllHealthCommentsAssociatedWithPatientID(int arg0) {
    }
}
