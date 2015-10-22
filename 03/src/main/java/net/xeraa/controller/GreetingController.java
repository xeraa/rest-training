package net.xeraa.controller;

import net.xeraa.model.Name;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

  private static final String template = "Hello %s!";
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping("/greeting")
  public Name name(@RequestParam(value="name", required=true) String name){
    return new Name(counter.incrementAndGet(), String.format(template, name));
  }

}
