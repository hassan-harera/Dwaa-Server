package com.harera.hayat.core.stub.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayat.core.model.food.FoodUnit;
import com.harera.hayat.core.repository.food.FoodUnitRepository;

@Service
public class FoodUnitStubs {

    @Autowired
    private FoodUnitRepository foodUnitRepository;

    public FoodUnit insert(String arabicName, String englishName) {
        FoodUnit foodUnit = create(arabicName, englishName);
        return foodUnitRepository.save(foodUnit);
    }

    private FoodUnit create(String arabicName, String englishName) {
        FoodUnit foodUnit = new FoodUnit();
        foodUnit.setId(0L);
        foodUnit.setArabicName(arabicName);
        foodUnit.setEnglishName(englishName);
        return foodUnit;
    }
}
