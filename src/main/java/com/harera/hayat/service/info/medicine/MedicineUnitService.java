package com.harera.hayat.service.info.medicine;

import static com.harera.hayat.util.ObjectMapperUtils.mapAll;

import java.util.List;

import org.springframework.stereotype.Service;

import com.harera.hayat.model.medicine.MedicineUnitResponse;
import com.harera.hayat.repository.medicine.MedicineUnitRepository;

@Service
public class MedicineUnitService {

    private final MedicineUnitRepository medicineUnitRepository;

    public MedicineUnitService(MedicineUnitRepository medicineUnitRepository) {
        this.medicineUnitRepository = medicineUnitRepository;
    }

    public List<MedicineUnitResponse> list() {
        return mapAll(medicineUnitRepository.findAll(), MedicineUnitResponse.class);
    }
}
