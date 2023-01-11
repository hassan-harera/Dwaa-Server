package com.harera.hayat.controller.donation.medicne;

import static com.harera.hayat.model.donation.DonationCategory.MEDICINE;
import static com.harera.hayat.model.donation.DonationState.PENDING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harera.hayat.ApplicationIT;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.donation.CommunicationMethod;
import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.medicine.MedicineDonation;
import com.harera.hayat.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayat.model.medicine.Medicine;
import com.harera.hayat.model.medicine.unit.MedicineUnit;
import com.harera.hayat.stub.city.CityStubs;
import com.harera.hayat.stub.donation.DonationStubs;
import com.harera.hayat.stub.donation.medicine.MedicineDonationStubs;
import com.harera.hayat.stub.medicine.MedicineStubs;
import com.harera.hayat.stub.medicine.MedicineUnitStubs;
import com.harera.hayat.util.DataUtil;
import com.harera.hayat.util.RequestUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MedicineDonationControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final DataUtil dataUtil;
    private final MedicineUnitStubs medicineUnitStubs;
    private final MedicineStubs medicineStubs;
    private final CityStubs cityStubs;
    private final MedicineDonationStubs medicineDonationStubs;
    private final DonationStubs donationStubs;
    private final ModelMapper modelMapper;

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

    @Test
    void get_with_then() {
        // Given
        String url = "/api/v1/donations/medicine/%d";
        String mobile = "01062227714";
        String password = "password";

        MedicineUnit unit = medicineUnitStubs.insert("arabicName", "englishName");
        Medicine medicine = medicineStubs.insert("arabicName", "englishName", unit);
        City city = cityStubs.insert("arabic_name", "english_name");
        Donation donation = donationStubs.insert("title", "description", city, MEDICINE,
                        PENDING);

        MedicineDonation medicineDonation = medicineDonationStubs.insert(unit, 1F,
                        medicine, OffsetDateTime.now().plusMonths(1), donation);

        try {
            // When
            ResponseEntity<MedicineDonationResponse> responseEntity = requestUtil
                            .getWithAuth(url.formatted(medicineDonation.getId()), null,
                                            null, MedicineDonationResponse.class);

            // Then
            MedicineDonationResponse response = responseEntity.getBody();
            assertEquals(200, responseEntity.getStatusCodeValue());
            assertNotNull(response);
            assertEquals(medicineDonation.getId(), response.getId());
            assertEquals(medicineDonation.getMedicine().getId(),
                            response.getMedicine().getId());
            assertEquals(medicineDonation.getUnit().getId(), response.getUnit().getId());
            assertEquals(medicineDonation.getAmount(), response.getAmount());
        } finally {
            // Cleanup
            dataUtil.delete(medicine, unit, city);
        }
    }

    @Test
    void list_thenValidateMapping() throws JsonProcessingException {
        // Given
        String url = "/api/v1/donations/medicine?page=1";
        String mobile = "01062227714";
        String password = "password";

        MedicineUnit unit = medicineUnitStubs.insert("arabicName", "englishName");
        Medicine medicine = medicineStubs.insert("arabicName", "englishName", unit);
        City city = cityStubs.insert("arabic_name", "english_name");
        Donation donation = donationStubs.insert("title", "description", city, MEDICINE,
                        PENDING);

        MedicineDonation medicineDonation1 = medicineDonationStubs.insert(unit, 1F,
                        medicine, OffsetDateTime.now().plusMonths(1), donation);
        MedicineDonation medicineDonation2 = medicineDonationStubs.insert(unit, 1F,
                        medicine, OffsetDateTime.now().plusMonths(1), donation);
        MedicineDonation medicineDonation3 = medicineDonationStubs.insert(unit, 1F,
                        medicine, OffsetDateTime.now().plusMonths(1), donation);
        MedicineDonation medicineDonation4 = medicineDonationStubs.insert(unit, 1F,
                        medicine, OffsetDateTime.now().plusMonths(1), donation);

        try {
            // When
            ResponseEntity<String> responseEntity =
                            requestUtil.getWithAuth(url, null, null, String.class);

            // Then
            assertEquals(200, responseEntity.getStatusCodeValue());
            MedicineDonationResponse[] response = new ObjectMapper().readValue(
                            responseEntity.getBody(), MedicineDonationResponse[].class);
            assertNotNull(response);
            // assert fields values of first tow
            assertEquals(4, response.length);
        } finally {
            // Cleanup
            dataUtil.delete(medicine, unit, city, donation, medicineDonation1,
                            medicineDonation2, medicineDonation3, medicineDonation4);
        }
    }

}
