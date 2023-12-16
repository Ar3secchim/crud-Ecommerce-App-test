package br.ada.ecommerce.test.customer;

import java.io.Serializable;

public class Customer implements Serializable {
  private String idTransaction;
  private String name;
  private String email;
  private String address;
  private String password;

  public String getIdTransaction() {
    return idTransaction;
  }

  public void setIdTransaction(String idTransaction) {
    this.idTransaction = idTransaction;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
