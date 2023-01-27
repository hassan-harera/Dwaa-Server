package com.harera.hayat.core.controller.donation.medicne;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.harera.hayat.core.ApplicationIT;
import com.harera.hayat.core.model.city.City;
import com.harera.hayat.core.model.donation.CommunicationMethod;
import com.harera.hayat.core.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.core.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayat.core.model.medicine.Medicine;
import com.harera.hayat.core.model.medicine.unit.MedicineUnit;
import com.harera.hayat.core.stub.city.CityStubs;
import com.harera.hayat.core.stub.medicine.MedicineStubs;
import com.harera.hayat.core.stub.medicine.MedicineUnitStubs;
import com.harera.hayat.core.util.DataUtil;
import com.harera.hayat.core.util.RequestUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MedicineDonationControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final DataUtil dataUtil;
    private final MedicineUnitStubs medicineUnitStubs;
    private final MedicineStubs medicineStubs;
    private final CityStubs cityStubs;

    @Test
    void create_with_then() {
        // Given
        String url = "/api/v1/donations/medicine";
        String mobile = "01062227714";
        String password = "password";

        MedicineUnit unit = medicineUnitStubs.insert("arabicName", "englishName");
        Medicine medicine = medicineStubs.insert("arabicName", "englishName", unit);
        City city = cityStubs.insert("arabic_name", "english_name");

        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineId(medicine.getId());
        request.setUnitId(unit.getId());
        request.setCityId(city.getId());
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setDonationExpirationDate(OffsetDateTime.now().plusMonths(1));

        try {
            // When
            ResponseEntity<MedicineDonationResponse> responseEntity =
                            requestUtil.postWithAuth(url, request, null,
                                            MedicineDonationResponse.class);

            // Then
            MedicineDonationResponse response = responseEntity.getBody();
            assertEquals(200, responseEntity.getStatusCodeValue());
            assertNotNull(response);
        } finally {
            // Cleanup
            dataUtil.delete(medicine, unit, city);
        }
    }

}
