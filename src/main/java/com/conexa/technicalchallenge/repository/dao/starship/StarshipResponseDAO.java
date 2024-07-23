package com.conexa.technicalchallenge.repository.dao.starship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarshipResponseDAO {
    private String message;
    private StarshipResultDAO result;

}
