Feature: Pet Lifecycle API Testing

  Scenario: Verify complete pet lifecycle using CRUD operations

    Given I have a new pet with a unique name and available status
    When I send a POST request to create the pet
    Then the pet should be created successfully
    And I extract the pet ID from the response

    When I send a GET request using the extracted pet ID
    Then the pet details should match the initial request

    When I update the pet status to "sold" using PUT request
    Then the pet status should be updated successfully

    When I send a DELETE request using the extracted pet ID
    Then the pet should be deleted successfully with status code 200

    When I send a GET request using the deleted pet ID
    Then the response status code should be 404
