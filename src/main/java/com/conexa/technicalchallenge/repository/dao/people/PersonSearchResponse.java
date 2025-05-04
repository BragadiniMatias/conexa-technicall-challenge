package com.conexa.technicalchallenge.repository.dao.people;
import lombok.Data;

import java.util.List;

@Data
public class PersonSearchResponse {
    private String message;
    private List<PersonResult> result;
}
