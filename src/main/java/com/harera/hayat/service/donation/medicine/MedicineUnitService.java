package com.harera.hayat.service.donation.medicine;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.medicine.unit.MedicineUnit;
import com.harera.hayat.repository.medicine.MedicineUnitRepository;

@Service
public class MedicineUnitService {

    private final MedicineUnitRepository medicineUnitRepository;

    public MedicineUnitService(MedicineUnitRepository medicineUnitRepository) {
        this.medicineUnitRepository = medicineUnitRepository;
    }

    public MedicineUnit getMedicineUnit(Long medicineUnitId) {
        return medicineUnitRepository.findById(medicineUnitId)
                        .orElseThrow(() -> new EntityNotFoundException(MedicineUnit.class,
                                        medicineUnitId));
    }

}
