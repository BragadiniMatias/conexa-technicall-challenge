package com.conexa.technicalchallenge.repository.dao.people;

import com.conexa.technicalchallenge.repository.dao.starship.StarshipResultDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleResponseDAO {
    private String message;
    private PeopleResultDAO result;
}
