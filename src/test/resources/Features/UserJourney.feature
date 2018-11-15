Feature: Reward

  Scenario: Provide Reward to new User

    Given New user created and his/her name is Test
    When He/She got reward from Company
    Then His/Her BQA balance should increased by 8