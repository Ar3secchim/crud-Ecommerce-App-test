package br.ada.ecommerce.test.hook;

import br.ada.ecommerce.test.customer.Customer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;

public class Hooks {
  public static Customer createCustomer() {
    RequestSpecification request = RestAssured.given()
            .baseUri("http://localhost:8080")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON);

    Response response;

    Customer customer = new Customer();
    customer.setName(RandomStringUtils.randomAlphabetic(10));
    customer.setEmail(RandomStringUtils.randomAlphabetic(10)+ "@gmail.com");
    customer.setAddress(RandomStringUtils.randomAlphabetic(10));
    customer.setPassword("@TesteEndtoEnd1");

    response = request.body(customer).when().post("/customer");
    response.then().statusCode(201);
    return customer;
  }
}
