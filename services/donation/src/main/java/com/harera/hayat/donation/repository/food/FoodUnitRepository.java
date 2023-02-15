package com.harera.hayat.donation.repository.food;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.harera.hayat.donation.model.food.FoodUnit;

public interface FoodUnitRepository extends MongoRepository<FoodUnit, Long> {
}
