package net.xeraa.controller;

import net.xeraa.model.Name;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class NameController {

  private final AtomicLong counter = new AtomicLong();

  @RequestMapping("/hello")
  public String name(@RequestParam(value="name", defaultValue="World") String name){
    return new Name(counter.incrementAndGet(), name).toString();
  }

}
