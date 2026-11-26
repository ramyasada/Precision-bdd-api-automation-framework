Feature: Cross Endpoint Data Consistency

  Scenario Outline: Verify pet data consistency across multiple endpoints

    Given I have a pet with category "<categoryName>" and status "<initialStatus>"

    When I create the pet using POST request

    Then the consistency pet should be created successfully

    And I extract the created pet ID

    When I update the created consistency pet status to "<updatedStatus>" using PUT request

    Then the created consistency pet status should be updated successfully

    When I retrieve the store inventory

    Then the inventory response should be successful

    When I retrieve pets with status "<updatedStatus>"

    Then the created pet ID should exist in the retrieved pets list

    Examples:
      | categoryName      | initialStatus | updatedStatus |
      | HighValue Bulldog | available     | sold          |