package net.xeraa.controller;

import net.xeraa.model.Name;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class NameController {

  private static final String TEMPLATE = "Hello %s!";

  @RequestMapping("/hello")
  @ResponseBody
  public HttpEntity<Name> nameGreeting(
      @RequestParam(value = "name", required = false, defaultValue = "Philipp") String name) {

    Name greeting = new Name(String.format(TEMPLATE, name));
    greeting.add(linkTo(methodOn(NameController.class).nameGreeting(name)).withSelfRel());

    return new ResponseEntity<>(greeting, HttpStatus.OK);
  }

}
