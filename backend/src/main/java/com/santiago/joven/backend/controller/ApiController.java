package com.santiago.joven.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** ApiController. */
@RestController
@RequestMapping("/api/v1")
public class ApiController {

  @GetMapping("/test")
  public String getMethodName() {
    return "Hola Mundo";
  }
}
