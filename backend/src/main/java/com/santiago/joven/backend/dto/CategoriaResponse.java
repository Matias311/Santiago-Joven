package com.santiago.joven.backend.dto;

import com.santiago.joven.backend.model.enums.TipoCategoria;
import java.util.UUID;
import lombok.Builder;

/** DTO de salida para una {@code Categoria}. */
@Builder
public record CategoriaResponse(
    UUID id, String nombre, String descripcion, String icono, String color, TipoCategoria tipo) {}
