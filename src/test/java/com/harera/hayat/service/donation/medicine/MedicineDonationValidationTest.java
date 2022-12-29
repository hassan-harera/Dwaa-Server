package com.harera.hayat.service.donation.medicine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.harera.hayat.common.exception.EntityNotFoundException;
import com.harera.hayat.common.exception.FieldLimitException;
import com.harera.hayat.common.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.CommunicationMethod;
import com.harera.hayat.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.medicine.MedicineRepository;
import com.harera.hayat.repository.medicine.MedicineUnitRepository;
import com.harera.hayat.util.ErrorCode;

@ExtendWith(MockitoExtension.class)
class MedicineDonationValidationTest {

    @Mock
    private CityRepository cityRepository;
    @Mock
    private MedicineUnitRepository medicineUnitRepository;
    @Mock
    private MedicineRepository medicineRepository;;

    private MedicineDonationValidation donationValidation;

    @BeforeEach
    void setUp() {
        donationValidation = new MedicineDonationValidation(cityRepository,
                        medicineUnitRepository, medicineRepository);
    }

    @Test
    void create_withoutTitle_thenMandatoryFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setExpirationDate(ZonedDateTime.now().plusMonths(1));
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
        assertTrue(ex instanceof MandatoryFieldException);
        assertEquals(ErrorCode.MANDATORY_DONATION_TITLE,
                        ((MandatoryFieldException) ex).getCode());
    }

    @Test
    void create_withoutAmount_thenMandatoryFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        Exception ex = null;
        try {
            donationValidation.validateCreate(request);
        } catch (Exception e) {
            ex = e;
            ex.printStackTrace();
        }

        // then
        assertNotNull(ex);
        assertEquals(FieldLimitException.class, ex.getClass());
        assertEquals(ErrorCode.FORMAT_DONATION_AMOUNT,
                        ((FieldLimitException) ex).getCode());
    }

    @Test
    void create_withoutCommunicationMethod_thenMandatoryFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setTitle("title");
        request.setExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        Exception ex = null;
        try {
            donationValidation.validateCreate(request);
        } catch (Exception e) {
            ex = e;
            ex.printStackTrace();
        }

        // then
        assertNotNull(ex);
        assertEquals(MandatoryFieldException.class, ex.getClass());
        assertEquals(ErrorCode.MANDATORY_DONATION_COMMUNICATION_METHOD,
                        ((MandatoryFieldException) ex).getCode());
    }

    @Test
    void create_withNotFoundCity_thenEntityNotFoundException() {
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
        request.setExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        when(cityRepository.existsById(1L)).thenReturn(false);

        Exception ex = null;
        try {
            donationValidation.validateCreate(request);
        } catch (Exception e) {
            ex = e;
            ex.printStackTrace();
        }

        // then
        assertNotNull(ex);
        assertEquals(EntityNotFoundException.class, ex.getClass());
        assertEquals(ErrorCode.NOT_FOUND_DONATION_CITY,
                        ((EntityNotFoundException) ex).getCode());
    }

    @Test
    void create_withNotFoundMedicine_thenEntityNotFoundException() {
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
        request.setExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        when(cityRepository.existsById(1L)).thenReturn(true);
        when(medicineUnitRepository.existsById(1L)).thenReturn(true);
        when(medicineRepository.existsById(1L)).thenReturn(false);

        Exception ex = null;
        try {
            donationValidation.validateCreate(request);
        } catch (Exception e) {
            ex = e;
            ex.printStackTrace();
        }

        // then
        assertNotNull(ex);
        assertEquals(EntityNotFoundException.class, ex.getClass());
        assertEquals(ErrorCode.NOT_FOUND_DONATION_MEDICINE,
                        ((EntityNotFoundException) ex).getCode());
    }

    @Test
    void create_withNotFoundUnit_thenEntityNotFoundException() {
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
        request.setExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        when(cityRepository.existsById(1L)).thenReturn(true);
        when(medicineUnitRepository.existsById(1L)).thenReturn(false);

        Exception ex = null;
        try {
            donationValidation.validateCreate(request);
        } catch (Exception e) {
            ex = e;
            ex.printStackTrace();
        }

        // then
        assertNotNull(ex);
        assertEquals(EntityNotFoundException.class, ex.getClass());
        assertEquals(ErrorCode.NOT_FOUND_DONATION_UNIT,
                        ((EntityNotFoundException) ex).getCode());
    }

    @Test
    void create_withValidRequest_then() {
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
        request.setExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        // then
    }

    @Test
    void create_with_then() {
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
        request.setExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        // then
    }

}
