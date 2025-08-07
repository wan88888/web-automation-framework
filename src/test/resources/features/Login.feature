Feature: SauceDemo Login

  Scenario: Successful login with valid credentials
    Given User is on SauceDemo login page
    When User enters valid username and password
    And User clicks on the login button
    Then User should be redirected to the home page