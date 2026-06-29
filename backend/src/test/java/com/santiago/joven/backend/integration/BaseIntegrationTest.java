package com.santiago.joven.backend.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

  @LocalServerPort protected int port;

  protected RestClient client() {
    return RestClient.create("http://localhost:" + port);
  }

  protected String token;

  protected RestClient authClient() {
    return authClient(token);
  }

  protected RestClient authClient(String bearerToken) {
    return RestClient.builder()
        .baseUrl("http://localhost:" + port)
        .defaultHeader("Authorization", "Bearer " + bearerToken)
        .build();
  }
}
