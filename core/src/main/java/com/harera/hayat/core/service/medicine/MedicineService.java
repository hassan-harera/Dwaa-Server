package com.harera.hayat.core.service.medicine;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.harera.hayat.core.model.medicine.Medicine;
import com.harera.hayat.core.model.medicine.MedicineResponse;
import com.harera.hayat.core.model.medicine.unit.MedicineUnitResponse;
import com.harera.hayat.core.repository.medicine.MedicineRepository;
import com.harera.hayat.core.repository.medicine.MedicineUnitRepository;
import com.harera.hayat.core.util.ObjectMapperUtils;

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
        return ObjectMapperUtils.mapAll(medicineUnitRepository.findAll(),
                        MedicineUnitResponse.class);
    }

    public List<MedicineResponse> list(String query, int page) {
        List<Medicine> medicineList = medicineRepository.search(query,
                        Pageable.ofSize(pageSize).withPage(page));
        return ObjectMapperUtils.mapAll(medicineList, MedicineResponse.class);
    }
}
