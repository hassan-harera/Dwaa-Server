package com.harera.hayat.service.donation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.CommunicationMethod;
import com.harera.hayat.util.ErrorCode;

@ExtendWith(MockitoExtension.class)
class DonationValidationTest {

    private DonationValidation donationValidation;

    @BeforeEach
    void setUp() {
        donationValidation = new DonationValidation();
    }

    @Test
    void validateCreate_withoutTitle_thenMandatoryFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        Exception ex = null;
        try {
            donationValidation.validateDonation(request);
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
        request.setDonationDate(OffsetDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setTitle("title");
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        Exception ex = null;
        try {
            donationValidation.validateDonation(request);
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
        request.setDonationDate(OffsetDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        // then
    }

    @Test
    void validateCreate_with_then() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        // then
    }

}
