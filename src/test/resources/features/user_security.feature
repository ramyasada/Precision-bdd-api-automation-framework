Feature: User Security and Negative API Testing

  Scenario: Verify user creation with invalid email
    Given I have user details with an invalid email
    When I send a POST request to create the user
    Then the user creation response should be successful

  Scenario: Verify retrieving a non-existing user
    When I retrieve a user with username "nonExistentUser123"
    Then the user retrieval response should return 404
    And the error message should indicate user not found
	
  Scenario: Verify login API response with invalid credentials
    When I login using username "invalidUser123" and password "wrongPassword"
    Then the login API response should be successful