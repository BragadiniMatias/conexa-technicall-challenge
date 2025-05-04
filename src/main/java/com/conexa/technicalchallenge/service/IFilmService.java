package com.conexa.technicalchallenge.service;

import com.conexa.technicalchallenge.domain.Film;

import java.util.List;

public interface IFilmService {

    List<Film> getAll();

    Film getById(final int id);

    /**
     * La API en si no tiene filtrado por nombre para los films
     * (o por lo menos no encontre una URL que asi me lo permitiese)
     * <p>
     * Lo que hago es invocar al getAll y hacer un filtrado en memoria
     * con un stream. No es lo recomendable, mas cuando se labura con muchos datos, siempre
     * el filtrado se tiene que hacer a nivel de base
     *
     * @param name el nombre del film a buscar
     * @return la instancia del film
     */
    Film getByName(final String name);

}
