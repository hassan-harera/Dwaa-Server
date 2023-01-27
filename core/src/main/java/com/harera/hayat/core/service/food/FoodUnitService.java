package com.harera.hayat.core.service.food;

import static com.harera.hayat.util.ObjectMapperUtils.mapAll;

import java.util.List;

import org.springframework.stereotype.Service;

import com.harera.core.model.food.FoodUnitResponse;
import com.harera.core.repository.food.FoodUnitRepository;
import com.harera.core.util.ObjectMapperUtils;
import com.harera.hayat.core.model.food.FoodUnitResponse;
import com.harera.hayat.core.repository.food.FoodUnitRepository;
import com.harera.hayat.core.util.ObjectMapperUtils;
import com.harera.hayat.model.food.FoodUnitResponse;
import com.harera.hayat.repository.food.FoodUnitRepository;

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
