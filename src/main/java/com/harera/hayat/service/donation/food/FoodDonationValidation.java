package com.harera.hayat.service.donation.food;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.service.donation.DonationValidation;
import com.harera.hayat.util.ErrorCode;

@Service
public class FoodDonationValidation {

    private final DonationValidation donationValidation;

    public FoodDonationValidation(DonationValidation donationValidation) {
        this.donationValidation = donationValidation;
    }

    public void validateCreate(FoodDonationRequest foodDonationRequest) {
        donationValidation.validateCreate(foodDonationRequest);
        validateMandatoryFields(foodDonationRequest);
        validateFieldsFormat(foodDonationRequest);
    }

    private void validateFieldsFormat(FoodDonationRequest foodDonationRequest) {
        if (foodDonationRequest.getAmount() < 0
                        || foodDonationRequest.getAmount() > 10000) {
            throw new FieldFormatException(ErrorCode.FORMAT_FOOD_DONATION_AMOUNT,
                            "amount",
                            foodDonationRequest.getAmount().toString());
        }
    }

    private void validateMandatoryFields(FoodDonationRequest foodDonationRequest) {
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
                            "expiration_date");
        }
    }
}
