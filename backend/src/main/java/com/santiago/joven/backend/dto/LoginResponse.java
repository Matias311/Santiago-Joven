package com.santiago.joven.backend.dto;

import java.util.List;
import java.util.UUID;

public record LoginResponse(String token, UUID userId, String email, List<String> roles) {}
