Feature: Manage Addresses
  As a registered user
  I want to be able to manage my addresses
  So that I can easily add, edit, and delete them

  Background:
    Given I am on myStore main page
    And I click the login button
    When I log in using my valid email "adam.kowalski@test.com" and password "Pa$$word":
    Then I should be on my account page

  Scenario Outline: Create and delete an address
    Given I go to the Addresses page
    When I click the Create new address button
    And I fill in the "<Alias>","<Address>", "<City>", "<PostalCode>", "<Phone>","<Country>"
    And I click the Save button
    And I can see success alert
    Then I verify provided details : "<Alias>","<Address>", "<City>", "<PostalCode>", "<Phone>", "<Country>" are correct
    When I click the Delete button for the created address
    Then I check if address was successfully deleted

    Examples:
      | Alias | Address         | Country        | City    | PostalCode | Phone       |
      | Work  | 123 Main Street | United Kingdom | Anytown | 12345      | +1-555-5555 |