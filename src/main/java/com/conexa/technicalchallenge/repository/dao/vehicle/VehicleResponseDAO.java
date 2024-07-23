package com.conexa.technicalchallenge.repository.dao.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponseDAO {
    private String message;
    private VehicleResultDAO result;

}
