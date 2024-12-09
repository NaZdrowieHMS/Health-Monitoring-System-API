package agh.edu.pl.healthmonitoringsystem.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MedicalTestResultSteps {

    @Given("A patient with ID {int} exists in the system")
    public void aPatientWithIDExistsInTheSystem(int arg0) {

    }

    @When("The patient uploads a new imaging test result")
    public void thePatientUploadsANewImagingTestResult() {
    }

    @Then("The HMS API should return status {int}")
    public void theHMSAPIShouldReturnStatus(int arg0) {
        
    }

    @And("The test result should be stored and accessible in the patient’s profile")
    public void theTestResultShouldBeStoredAndAccessibleInThePatientSProfile() {
    }
}
