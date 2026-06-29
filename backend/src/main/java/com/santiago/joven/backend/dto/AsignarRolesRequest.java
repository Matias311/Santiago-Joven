package com.santiago.joven.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

public record AsignarRolesRequest(@NotEmpty Set<UUID> rolIds) {}
