package br.ada.ecommerce.test.authentication;

import br.ada.ecommerce.test.customer.Customer;
import br.ada.ecommerce.test.hook.Hooks;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.junit.jupiter.api.Assertions;

public class AuthenticationDefinitions {
  private Customer customer = new Customer();
  private Response response = null;
  private Authentication login = new Authentication();

  private RequestSpecification request = RestAssured.given()
          .baseUri("http://localhost:8080")
          .contentType(ContentType.JSON)
          .accept(ContentType.JSON);

  @Before
  public void beforeScenario() {
   customer = Hooks.createCustomer();
  }

  @Given("the customer is not authenticated")
  public void theCustomerIsNotAuthenticated() {
    login = new Authentication();
    login.setEmail(customer.getEmail());
    login.setPassword(customer.getPassword() + "1");

    response = request.body(login).when().post("/login");
    response.then().statusCode(403);
  }

  @When("the customer logs in with valid credentials")
  public void theCustomerLogsInWithValidCredentials() {
    login = new Authentication();
    login.setEmail(customer.getEmail());
    login.setPassword(customer.getPassword());

    response = request.body(login).when().post("/login");
    response.then().statusCode(200);
  }

  @Then("the customer is successfully authenticated")
  public void theCustomerIsSuccessfullyAuthenticated() {
    login.setEmail(customer.getEmail());
    login.setPassword(customer.getPassword());

    response = request.body(login).when().post("/login");
    response.then().statusCode(200);
    String token = response.jsonPath().get("token");

    Assertions.assertFalse(token.isEmpty());
  }

  @Given("the customer without an email")
  public void theCustomerWithoutAnEmail() {
    login = new Authentication();
    login.setEmail("unites@gmail.com");
    login.setPassword("@TesteEndtoEnd");

    response = request.body(login).when().post("/login");
    response.then().statusCode(400);
  }

  @When("the customer tries to register without providing an email")
  public void theCustomerTriesToRegisterWithoutProvidingAnEmail() {
    login = new Authentication();
    login.setEmail("unites@gmail.com");
    login.setPassword("@TesteEndtoEnd");

    response = request.body(login).when().post("/login");
    response.then().statusCode(400);

    String errors = response.jsonPath().get("errors");
    String code = response.jsonPath().get("code");

    Assertions.assertEquals("Customer not found", errors);
    Assertions.assertEquals("BAD_REQUEST", code);
  }

  @Then("the customer remains unknown")
  public void theCustomerRemainsUnknown() {
    login.setEmail("unites@gmail.com");
    login.setPassword("@TesteEndtoEnd");

    response = request.body(login).when().post("/login");
    response.then().statusCode(400);
  }
}
