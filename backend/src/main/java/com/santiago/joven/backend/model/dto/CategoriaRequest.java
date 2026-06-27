package com.santiago.joven.backend.model.dto;

import com.santiago.joven.backend.model.enums.TipoCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/** DTO de entrada para crear una {@code Categoria}. */
@Builder
public record CategoriaRequest(
    @NotBlank String nombre,
    String descripcion,
    String icono,
    String color,
    @NotNull TipoCategoria tipo) {}
