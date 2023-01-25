package com.harera.hayat.stub.medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.donation.medicine.Medicine;
import com.harera.hayat.model.donation.medicine.MedicineUnit;
import com.harera.hayat.repository.medicine.MedicineRepository;

@Service
public class MedicineStubs {

    @Autowired
    private MedicineRepository medicineUnitRepository;

    public Medicine insert(String arabicName, String englishName, MedicineUnit unit) {
        Medicine medicineUnit = create(arabicName, englishName);
        medicineUnit.setUnit(unit);
        return medicineUnitRepository.save(medicineUnit);
    }

    private Medicine create(String arabicName, String englishName) {
        Medicine medicineUnit = new Medicine();
        medicineUnit.setArabicName(arabicName);
        medicineUnit.setEnglishName(englishName);
        return medicineUnit;
    }
}
