package com.harera.hayatserver.repository.food;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayatserver.model.food.FoodUnit;

public interface FoodUnitRepository extends JpaRepository<FoodUnit, Long> {
}