Feature: Login Customer
  Scenario: Successful Login
    Given the customer is not authenticated
    When the customer logs in with valid credentials
    Then the customer is successfully authenticated

  Scenario: Customer without Email
    Given the customer without an email
    When the customer tries to register without providing an email
    Then the customer remains unknown
