package com.harera.hayat.service.medicine;

import static com.harera.hayat.util.ObjectMapperUtils.mapAll;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.medicine.Medicine;
import com.harera.hayat.model.medicine.MedicineResponse;
import com.harera.hayat.model.medicine.unit.MedicineUnitResponse;
import com.harera.hayat.repository.medicine.MedicineRepository;
import com.harera.hayat.repository.medicine.MedicineUnitRepository;

@Service
public class MedicineService {

    private final MedicineUnitRepository medicineUnitRepository;
    private final int pageSize;
    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineUnitRepository medicineUnitRepository,
                    @Value("${medicines.page_size}") int pageSize,
                    MedicineRepository medicineRepository) {
        this.medicineUnitRepository = medicineUnitRepository;
        this.pageSize = pageSize;
        this.medicineRepository = medicineRepository;
    }

    public List<MedicineUnitResponse> listUnits() {
        return mapAll(medicineUnitRepository.findAll(), MedicineUnitResponse.class);
    }

    public List<MedicineResponse> list(String query, int page) {
        List<Medicine> medicineList = medicineRepository.search(query,
                        Pageable.ofSize(pageSize).withPage(page));
        return mapAll(medicineList, MedicineResponse.class);
    }

    public Medicine getMedicine(Long medicineId) {
        return medicineRepository.findById(medicineId).orElseThrow(
                        () -> new EntityNotFoundException(Medicine.class, medicineId));
    }
}
