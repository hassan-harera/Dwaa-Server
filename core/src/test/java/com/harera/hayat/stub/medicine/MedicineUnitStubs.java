package com.harera.hayat.stub.medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayat.common.model.medicine.MedicineUnit;
import com.harera.hayat.repository.medicine.MedicineUnitRepository;

@Service
public class MedicineUnitStubs {

    @Autowired
    private MedicineUnitRepository medicineUnitRepository;

    public MedicineUnit insert(String arabicName, String englishName) {
        MedicineUnit medicineUnit = create(arabicName, englishName);
        return medicineUnitRepository.save(medicineUnit);
    }

    private MedicineUnit create(String arabicName, String englishName) {
        MedicineUnit medicineUnit = new MedicineUnit();
        medicineUnit.setId(0L);
        medicineUnit.setArabicName(arabicName);
        medicineUnit.setEnglishName(englishName);
        return medicineUnit;
    }
}
