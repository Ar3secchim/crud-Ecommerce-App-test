package br.ada.ecommerce.test.customer;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;

import java.util.UUID;

public class CustomerDefinitions {
  private RequestSpecification request = RestAssured.given()
          .baseUri("http://localhost:8080")
          .contentType(ContentType.JSON)
          .accept(ContentType.JSON);

  private Response response = null;
  private Customer customer = new Customer();
  private String idTransaction = null;

  @Given("customer is unknown")
  public void customerIsUnknown() {
    customer = new Customer();
    customer.setName(RandomStringUtils.randomAlphabetic(10));
    customer.setEmail(RandomStringUtils.randomAlphabetic(10)+ "@gmail.com");
    customer.setAddress(RandomStringUtils.randomAlphabetic(10));
    customer.setPassword("@TesteEndtoEnd1");
  }

  @When("customer is registered")
  public void customerIsRegistered() {
    response = request.body(customer).when().post("/customer");
    response.then().statusCode(201);

    idTransaction = response.jsonPath().get("idTransaction");

    String name = response.jsonPath().get("name");
    String email = response.jsonPath().get("email");

    Assertions.assertEquals(customer.getName(), name);
    Assertions.assertEquals(customer.getEmail(), email);
  }

  @Then("customer is know")
  public void customerIsKnow() {
    response = request.when().get("/customer/"+ idTransaction);
    response.then().statusCode(200);

    String id = response.jsonPath().get("idTransaction");
    String name = response.jsonPath().get("name");
    String email = response.jsonPath().get("email");
    String address = response.jsonPath().get("address");

    Assertions.assertEquals(idTransaction, id);
    Assertions.assertEquals(customer.getName(), name);
    Assertions.assertEquals(customer.getEmail(), email);
    Assertions.assertEquals(customer.getAddress(), address);
  }

  @Given("customer without email")
  public void customerWithoutEmail() {
    customer = new Customer();
    customer.setName(RandomStringUtils.randomAlphabetic(20));
    customer.setAddress(RandomStringUtils.randomAlphabetic(6));
    customer.setPassword("@TesteEndtoEnd1");
    customer.setIdTransaction(UUID.randomUUID().toString());
    customer.setEmail(RandomStringUtils.randomAlphabetic(2));
  }

  @When("customer is registered with fail")
  public void customerIsRegisteredWithFail() {
    response = request.body(customer).when().post("/customer");
    response.then().statusCode(400);
  }

  @Then("customer has been unknown")
  public void customerHasBeenUnknown() {
    response = request.when().get("/customer/" + customer.getIdTransaction());
    response.then().statusCode(400);

    String errors = response.jsonPath().get("errors");
    String code = response.jsonPath().get("code");

    Assertions.assertEquals("Customer not found with ID: " + customer.getIdTransaction(), errors);
    Assertions.assertEquals("BAD_REQUEST", code);
  }
}
