package com.harera.hayat.service.donations.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.CommunicationMethod;
import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.service.donation.DonationValidation;
import com.harera.hayat.service.donation.food.FoodDonationValidation;
import com.harera.hayat.util.ErrorCode;

@ExtendWith(MockitoExtension.class)
class FoodDonationValidationTest {

    @Mock
    private DonationValidation donationValidation;

    private FoodDonationValidation foodDonationValidation;

    @BeforeEach
    void setUp() {
        foodDonationValidation = new FoodDonationValidation(donationValidation);
    }

    @Test
    void validateCreate_withoutAmount_thenThrowMandatoryFieldException() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(1L);
        request.setUnitId(1L);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when

        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            foodDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_FOOD_DONATION_AMOUNT, ex.getCode());
    }

    @Test
    void validateCreate_withoutUnit_thenThrowMandatoryFieldException() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();

        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setAmount(15f);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            foodDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_FOOD_DONATION_UNIT, ex.getCode());
    }

    @Test
    void validateCreate_withoutFoodExpirationDate_thenThrowMandatoryFieldException() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();

        request.setCityId(1L);
        request.setUnitId(1L);
        request.setAmount(15f);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            foodDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_FOOD_DONATION_FOOD_EXPIRATION_DATE,
                        ex.getCode());
    }

    @Test
    void validateCreate_withInvalidAmountFormat_thenThrowFormatFieldException() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());

        request.setUnitId(1L);
        request.setAmount(-1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        FieldFormatException ex = assertThrows(FieldFormatException.class, () -> {
            foodDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.FORMAT_FOOD_DONATION_AMOUNT, ex.getCode());
    }
}
