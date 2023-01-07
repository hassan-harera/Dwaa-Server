package com.harera.hayat.service.info.food;

import static com.harera.hayat.util.ObjectMapperUtils.mapAll;

import java.util.List;

import org.springframework.stereotype.Service;

import com.harera.hayat.model.food.FoodUnitResponse;
import com.harera.hayat.repository.food.FoodUnitRepository;

@Service
public class FoodUnitService {

    private final FoodUnitRepository foodUnitRepository;

    public FoodUnitService(FoodUnitRepository foodUnitRepository) {
        this.foodUnitRepository = foodUnitRepository;
    }

    public List<FoodUnitResponse> list() {
        return mapAll(foodUnitRepository.findAll(), FoodUnitResponse.class);
    }
}
