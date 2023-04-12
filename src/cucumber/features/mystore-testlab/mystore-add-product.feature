Feature: Purchase a Hummingbird Printed Sweater

  Scenario Outline: Purchase a sweater and check order status
    Given the user is logged in with the credentials from the first task
    When the user selects the <product> and verifies a discount is applied
    And the user selects <size>
    And the user selects <qty> pieces
    And the user adds the product to cart
    And the user proceeds to checkout
    And the user confirms the address
    And the user selects the Pick up in store option
    And the user selects Pay by Check option
    And the user clicks Order with an obligation to pay
    And the user takes a screenshot of the order confirmation with the total amount
    And the user navigates to order history and details page
    Then the order with <requiredOrderStatus> status and the same total amount is displayed in the list
    Examples:
      | qty | requiredOrderStatus      | size | product                       |
      | 5   | "Awaiting check payment" | "M"  | "Hummingbird Printed Sweater" |