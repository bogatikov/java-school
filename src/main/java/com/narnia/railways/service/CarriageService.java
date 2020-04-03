package com.narnia.railways.service;

import com.narnia.railways.model.Carriage;
import com.narnia.railways.service.dto.CarriageDTO;

import java.util.List;

public interface CarriageService {

    List<Carriage> getCarriageListByTrain(Long trainId);

    List<CarriageDTO> getCarriageListWithPassenger(Long trainId);
}
