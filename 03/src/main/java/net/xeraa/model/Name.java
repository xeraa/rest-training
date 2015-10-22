package net.xeraa.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.hateoas.ResourceSupport;


public class Name extends ResourceSupport {

  private final String name;

  @JsonCreator
  public Name(@JsonProperty("name") String name) {
    this.name = name;
  }

}
