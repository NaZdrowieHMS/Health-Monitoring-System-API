Feature: Medical test result management

  Scenario: Upload a medical test result
    Given A patient with ID 1 exists in the system
    When The patient uploads a new imaging test result
    Then The HMS API should return status 201
    And The test result should be stored and accessible in the patient’s profile
