Feature: Patient doctor management

  Scenario: Assign a new patient to a doctor
    Given The patient is unassigned and available in the system
    When The doctor searches for the patient by PESEL or name and assigns them to their account
    Then The patient should be linked to the doctor in the system