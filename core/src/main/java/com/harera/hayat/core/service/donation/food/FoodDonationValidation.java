package com.harera.hayat.core.service.donation.food;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.harera.core.exception.FieldFormatException;
import com.harera.core.exception.MandatoryFieldException;
import com.harera.core.model.donation.food.FoodDonationDto;
import com.harera.core.model.donation.food.FoodDonationUpdateRequest;
import com.harera.core.util.ErrorCode;
import com.harera.hayat.core.exception.FieldFormatException;
import com.harera.hayat.core.exception.MandatoryFieldException;
import com.harera.hayat.core.model.donation.food.FoodDonationDto;
import com.harera.hayat.core.model.donation.food.FoodDonationUpdateRequest;
import com.harera.hayat.core.util.ErrorCode;
import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.food.FoodDonationDto;
import com.harera.hayat.model.donation.food.FoodDonationUpdateRequest;
import com.harera.hayat.service.donation.DonationValidation;
import com.harera.hayat.util.ErrorCode;

@Service
public class FoodDonationValidation {

    private final DonationValidation donationValidation;

    public FoodDonationValidation(DonationValidation donationValidation) {
        this.donationValidation = donationValidation;
    }

    public void validateCreate(FoodDonationDto foodDonationRequest) {
        donationValidation.validate(foodDonationRequest);
        validateMandatory(foodDonationRequest);
        validateFormat(foodDonationRequest);
    }

    private void validateFormat(FoodDonationDto foodDonationRequest) {
        if (foodDonationRequest.getAmount() <= 0
                        || foodDonationRequest.getAmount() > 10000) {
            throw new FieldFormatException(ErrorCode.FORMAT_FOOD_DONATION_AMOUNT,
                            "amount",
                            foodDonationRequest.getAmount().toString());
        }
        if (foodDonationRequest.getFoodExpirationDate().isBefore(OffsetDateTime.now())) {
            throw new FieldFormatException(
                            ErrorCode.FORMAT_FOOD_DONATION_FOOD_EXPIRATION_DATE,
                            "food expiration date",
                            foodDonationRequest.getFoodExpirationDate().toString());
        }
    }

    private void validateMandatory(FoodDonationDto foodDonationRequest) {
        if (foodDonationRequest.getAmount() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_FOOD_DONATION_AMOUNT,
                            "amount");
        }
        if (foodDonationRequest.getUnitId() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_FOOD_DONATION_UNIT,
                            "unit");
        }
        if (foodDonationRequest.getFoodExpirationDate() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_FOOD_DONATION_FOOD_EXPIRATION_DATE,
                            "food expiration date");
        }
    }

    public void validateUpdate(Long id, FoodDonationUpdateRequest request) {
        donationValidation.validate(request);
        validateMandatory(request);
        validateFormat(request);
    }
}
