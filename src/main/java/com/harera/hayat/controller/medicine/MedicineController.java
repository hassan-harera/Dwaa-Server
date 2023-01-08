package com.harera.hayat.controller.medicine;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.medicine.MedicineResponse;
import com.harera.hayat.model.medicine.unit.MedicineUnitResponse;
import com.harera.hayat.service.medicine.MedicineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/v1/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineUnitService) {
        this.medicineService = medicineUnitService;
    }

    @GetMapping("/units")
    @Operation(summary = "List", description = "List medicine units",
                    tags = "Medicine-Unit", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<MedicineUnitResponse>> list() {
        return ResponseEntity.ok(medicineService.listUnits());
    }

    @GetMapping("/search")
    @Operation(summary = "Search", description = "Search medicines", tags = "Medicines",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<MedicineResponse>> list(@RequestParam("q") String query,
                    @RequestParam("page") int page) {
        return ResponseEntity.ok(medicineService.list(query, page));
    }
}
