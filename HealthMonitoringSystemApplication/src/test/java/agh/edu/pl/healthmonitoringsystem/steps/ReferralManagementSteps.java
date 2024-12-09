package agh.edu.pl.healthmonitoringsystem.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReferralManagementSteps {

    @Given("A patient with ID {int} has multiple referrals in the system")
    public void aPatientWithIDHasMultipleReferralsInTheSystem(int arg0) {
    }

    @When("The patient views their referrals list")
    public void thePatientViewsTheirReferralsList() {
        
        
    }

    @Then("The HMS API should return all referrals associated with the patient")
    public void theHMSAPIShouldReturnAllReferralsAssociatedWithThePatient() {
    }

    @And("Each referral should include detailed comments, if any")
    public void eachReferralShouldIncludeDetailedCommentsIfAny() {
    }
}
