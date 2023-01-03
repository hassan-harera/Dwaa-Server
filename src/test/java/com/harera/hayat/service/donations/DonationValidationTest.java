package com.harera.hayat.service.donations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.CommunicationMethod;
import com.harera.hayat.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.service.donation.DonationValidation;
import com.harera.hayat.util.ErrorCode;

@ExtendWith(MockitoExtension.class)
class DonationValidationTest {

    @Mock
    private CityRepository cityRepository;

    private DonationValidation donationValidation;

    @BeforeEach
    void setUp() {
        donationValidation = new DonationValidation(cityRepository);
    }

    @Test
    void validateCreate_withoutTitle_thenMandatoryFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        Exception ex = null;
        try {
            donationValidation.validateCreate(request);
        } catch (Exception e) {
            ex = e;
        }

        // then
        assertNotNull(ex);
        assertEquals(MandatoryFieldException.class, ex.getClass());
        assertEquals(ErrorCode.MANDATORY_DONATION_TITLE,
                        ((MandatoryFieldException) ex).getCode());
    }

    @Test
    void validateCreate_withoutCommunicationMethod_thenMandatoryFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setTitle("title");
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        Exception ex = null;
        try {
            donationValidation.validateCreate(request);
        } catch (Exception e) {
            ex = e;

        }

        // then
        assertNotNull(ex);
        assertEquals(MandatoryFieldException.class, ex.getClass());
        assertEquals(ErrorCode.MANDATORY_DONATION_COMMUNICATION_METHOD,
                        ((MandatoryFieldException) ex).getCode());
    }

    @Test
    void validateCreate_withValidRequest_then() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        // then
    }

    @Test
    void validateCreate_with_then() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        // then
    }

}
