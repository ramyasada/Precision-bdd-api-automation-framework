Feature: Pet Inventory Analysis

  Scenario: Verify available pet count in inventory against available pets list

    When I retrieve the pet inventory
    Then the inventory should contain the available pet count

    When I retrieve all pets with status "available"
    Then the available pets count should match the inventory count
