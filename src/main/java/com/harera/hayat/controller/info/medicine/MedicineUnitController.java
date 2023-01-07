package com.harera.hayat.controller.info.medicine;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.medicine.MedicineUnitResponse;
import com.harera.hayat.service.info.medicine.MedicineUnitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/v1/medicine/units")
public class MedicineUnitController {

    private final MedicineUnitService medicineUnitService;

    public MedicineUnitController(MedicineUnitService medicineUnitService) {
        this.medicineUnitService = medicineUnitService;
    }

    @GetMapping
    @Operation(summary = "List", description = "List medicine units",
                    tags = "Medicine-Unit", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<MedicineUnitResponse>> list() {
        return ResponseEntity.ok(medicineUnitService.list());
    }
}
