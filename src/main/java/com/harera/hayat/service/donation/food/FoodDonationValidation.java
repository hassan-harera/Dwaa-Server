package com.harera.hayat.service.donation.food;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.FieldLimitException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.util.ErrorCode;
import com.harera.hayat.util.time.ZonedDateTimeUtils;

@Service
public class FoodDonationValidation {

    public void validateCreate(FoodDonationRequest foodDonationRequest) {
        validateMandatoryFields(foodDonationRequest);
        validateFieldsFormat(foodDonationRequest);
    }

    private void validateFieldsFormat(FoodDonationRequest foodDonationRequest) {
        if (foodDonationRequest.getAmount() < 0) {
            throw new FieldLimitException(ErrorCode.FORMAT_DONATION_AMOUNT, "amount",
                            foodDonationRequest.getAmount().toString());
        }
        if (foodDonationRequest.getAmount() > 100000) {
            throw new FieldLimitException(ErrorCode.FORMAT_DONATION_AMOUNT, "amount",
                            foodDonationRequest.getAmount().toString());
        }
        if (foodDonationRequest.getUnitId() < 0) {
            throw new FieldLimitException(ErrorCode.FORMAT_UNIT, "unit",
                            foodDonationRequest.getAmount().toString());
        }
        if (!ZonedDateTimeUtils.INSTANCE.isValidZonedDateTime(
                        foodDonationRequest.getFoodExpirationDate())) {
            throw new FieldFormatException(ErrorCode.FORMAT_EXPIRATION_DATE,
                            "expiration_date",
                            foodDonationRequest.getFoodExpirationDate());
        }
    }

    private void validateMandatoryFields(FoodDonationRequest foodDonationRequest) {
        if (foodDonationRequest.getTitle() == null
                        || !StringUtils.hasText(foodDonationRequest.getTitle())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_DONATION_TITLE,
                            "title");
        }
        if (foodDonationRequest.getAmount() == 0) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_DONATION_AMOUNT,
                            "amount");
        }
        if (foodDonationRequest.getUnitId() == 0) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_DONATION_AMOUNT,
                            "Unit");
        }
        if (foodDonationRequest.getCommunicationMethod() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_COMMUNICATION,
                            "communication_method");
        }
        if (foodDonationRequest.getFoodExpirationDate() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_DONATION_EXPIRATION_DATE,
                            "expiration_date");
        }
    }
}
