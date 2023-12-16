Feature:  Customer Register
  Scenario:
    Given customer is unknown
    When customer is registered
    Then customer is know

  Scenario: Customer without email
    Given customer without email
    When customer is registered with fail
    Then customer has been unknown
