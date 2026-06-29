package com.santiago.joven.backend.dto;

import com.santiago.joven.backend.model.enums.TipoCategoria;
import lombok.Builder;

/** DTO para actualizar una {@code Categoria}. */
@Builder
public record CategoriaUpdate(
    String nombre, String descripcion, String icono, String color, TipoCategoria tipo) {}
