package com.conexa.technicalchallenge.domain.helpers.wrappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * La principal idea es tener un objeto generico que pueda almacenar la informacion de la paginacion que
 * llega directamente de la consulta a la API para que la capa de dominio quede agnostico a ello
 *
 * @param <T> el tipo de dato
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericContentPaginationWrapper<T> {
    private List<T> content;
    private int totalPages;
    private int totalRecords;
}
