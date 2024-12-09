Feature: Health comment

  Scenario: Create a health comment
    Given The patient with ID 1 and the doctor with ID 2
    When The doctor adds a comment "Health comment"
    Then The hms api should return status 201
    And The health comment should be created properly with "Health comment" content

  Scenario: Retrieve patient's health comments
    Given The patient with ID 1 has existing health comments added by multiple doctors
    When The doctor requests for the patient's health comments
    Then The hms api should return all health comments associated with patient ID 1