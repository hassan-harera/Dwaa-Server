package com.harera.hayat.core.controller.food;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.core.model.food.FoodUnitResponse;
import com.harera.hayat.core.service.food.FoodUnitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/v1/food/units")
public class FoodUnitController {

    private final FoodUnitService foodUnitService;

    public FoodUnitController(FoodUnitService foodUnitService) {
        this.foodUnitService = foodUnitService;
    }

    @GetMapping
    @Operation(summary = "List", description = "List food units", tags = "Food-Unit",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<FoodUnitResponse>> list() {
        return ResponseEntity.ok(foodUnitService.list());
    }
}
