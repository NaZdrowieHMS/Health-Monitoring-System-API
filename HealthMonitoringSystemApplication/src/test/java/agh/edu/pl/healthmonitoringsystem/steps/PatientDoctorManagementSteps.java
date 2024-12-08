package agh.edu.pl.healthmonitoringsystem.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PatientDoctorManagementSteps {

    @Given("The patient is unassigned and available in the system")
    public void thePatientIsUnassignedAndAvailableInTheSystem() {
    }

    @When("The doctor searches for the patient by PESEL or name and assigns them to their account")
    public void theDoctorSearchesForThePatientByPESELOrNameAndAssignsThemToTheirAccount() {

    }

    @Then("The patient should be linked to the doctor in the system")
    public void thePatientShouldBeLinkedToTheDoctorInTheSystem() {
    }
}
