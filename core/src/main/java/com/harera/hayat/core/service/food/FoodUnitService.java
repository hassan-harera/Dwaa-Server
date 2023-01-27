package com.harera.hayat.core.service.food;

import java.util.List;

import org.springframework.stereotype.Service;

import com.harera.hayat.core.model.food.FoodUnitResponse;
import com.harera.hayat.core.repository.food.FoodUnitRepository;
import com.harera.hayat.core.util.ObjectMapperUtils;

@Service
public class FoodUnitService {

    private final FoodUnitRepository foodUnitRepository;

    public FoodUnitService(FoodUnitRepository foodUnitRepository) {
        this.foodUnitRepository = foodUnitRepository;
    }

    public List<FoodUnitResponse> list() {
        return ObjectMapperUtils.mapAll(foodUnitRepository.findAll(),
                        FoodUnitResponse.class);
    }
}
