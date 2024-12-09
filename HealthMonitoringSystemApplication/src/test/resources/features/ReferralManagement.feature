Feature: Referral management

  Scenario: Access patient referrals
    Given A patient with ID 1 has multiple referrals in the system
    When The patient views their referrals list
    Then The HMS API should return all referrals associated with the patient
    And Each referral should include detailed comments, if any