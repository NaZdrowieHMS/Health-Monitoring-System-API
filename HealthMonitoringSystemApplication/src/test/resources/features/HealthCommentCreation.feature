Feature: Health Monitoring System

  Scenario: Create a health comment
    Given The patient with ID 1 and the doctor with ID 2
    When The doctor adds a comment "Health comment"
    Then The hms api should return status 201
    And The health comment should be created properly with "Health comment" content