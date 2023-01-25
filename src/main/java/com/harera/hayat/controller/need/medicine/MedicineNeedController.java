package com.harera.hayat.controller.need.medicine;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.need.medicine.MedicineNeedRequest;
import com.harera.hayat.model.need.medicine.MedicineNeedResponse;
import com.harera.hayat.service.need.MedicineNeedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/medicine-needs")
@Tag(name = "Medicine-Need")
public class MedicineNeedController {

    private final MedicineNeedService medicineNeedService;

    public MedicineNeedController(MedicineNeedService medicineNeedService) {
        this.medicineNeedService = medicineNeedService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create a medicine need",
                    tags = "Food-Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineNeedResponse> create(
                    @RequestBody MedicineNeedRequest medicineNeedRequest,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        var response = medicineNeedService.create(medicineNeedRequest,
                        token.substring(7));
        return ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a medicine need",
                    tags = "Medicine-Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineNeedResponse> get(@PathVariable("id") Long id) {
        return ok(medicineNeedService.get(id));
    }
}
