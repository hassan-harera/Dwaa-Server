package com.harera.hayatserver.service.donation;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.harera.hayatserver.exception.EntityNotFoundException;
import com.harera.hayatserver.exception.FieldLimitException;
import com.harera.hayatserver.exception.FormatFieldException;
import com.harera.hayatserver.exception.MandatoryFieldException;
import com.harera.hayatserver.model.donation.FoodDonationRequest;
import com.harera.hayatserver.model.donation.PropertyDonationRequest;
import com.harera.hayatserver.model.food.FoodUnit;
import com.harera.hayatserver.repository.city.CityRepository;
import com.harera.hayatserver.repository.food.FoodUnitRepository;
import com.harera.hayatserver.util.ErrorCode;
import com.harera.hayatserver.util.time.ZonedDateTimeUtils;
import com.harera.hayatserver.validation.PagingValidation;

@Service
public class DonationValidation {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private FoodUnitRepository foodUnitRepository;

    public void validateList(Integer page, Integer size) {
        PagingValidation.validate(page, size);
    }

    public void validateDonateProperty(PropertyDonationRequest propertyDonationRequest) {
        checkFieldsMandatory(propertyDonationRequest);
        checkFieldsFormat(propertyDonationRequest);
        checkEntities(propertyDonationRequest);
    }

    private void checkEntities(PropertyDonationRequest propertyDonationRequest) {
        if (!cityRepository.existsById(propertyDonationRequest.getCityId())) {
            throw new RuntimeException("City not found");
        }
    }

    private void checkFieldsFormat(PropertyDonationRequest propertyDonationRequest) {
        if (propertyDonationRequest.getTitle().length() > 50) {
            throw new IllegalArgumentException("Title is too long");
        }

        if (propertyDonationRequest.getDescription().length() > 500) {
            throw new IllegalArgumentException("Description is too long");
        }

        if (propertyDonationRequest.getRooms() > 50) {
            throw new IllegalArgumentException("Rooms number must be less than 50");
        }

        if (propertyDonationRequest.getBathrooms() > 10) {
            throw new IllegalArgumentException(
                            "Bathrooms number must be less" + " than 10");
        }

        if (propertyDonationRequest.getFloors() > 10) {
            throw new IllegalArgumentException("Floors number must be less " + "than 10");
        }

        if (propertyDonationRequest.getKitchens() > 10) {
            throw new IllegalArgumentException(
                            "Kitchens number must be less " + "than 10");
        }

        if (propertyDonationRequest.getAvailableFrom()
                        .isAfter(propertyDonationRequest.getAvailableTo())) {
            throw new IllegalArgumentException(
                            "Available from must be before " + "available to");
        }

        if (propertyDonationRequest.getAvailableFrom().isBefore(ZonedDateTime.now())) {
            throw new IllegalArgumentException("Available from must be after " + "now");
        }

        if (propertyDonationRequest.getAvailableTo().isBefore(ZonedDateTime.now())) {
            throw new IllegalArgumentException("Available to must be after " + "now");
        }
    }

    private void checkFieldsMandatory(PropertyDonationRequest propertyDonationRequest) {
        if (propertyDonationRequest.getTitle() == null
                        || StringUtils.hasText(propertyDonationRequest.getTitle())) {
            throw new IllegalArgumentException("Title is mandatory");
        }
        if (propertyDonationRequest.getRooms() == null) {
            throw new IllegalArgumentException("Rooms is mandatory");
        }
        if (propertyDonationRequest.getBathrooms() == null) {
            throw new IllegalArgumentException("Bathrooms is mandatory");
        }
        if (propertyDonationRequest.getFloors() == null) {
            throw new IllegalArgumentException("Floors is mandatory");
        }
        if (propertyDonationRequest.getKitchens() == null) {
            throw new IllegalArgumentException("Kitchens is mandatory");
        }
        if (propertyDonationRequest.getAvailableFrom() == null) {
            throw new IllegalArgumentException("AvailableFrom is mandatory");
        }
        if (propertyDonationRequest.getAvailableTo() == null) {
            throw new IllegalArgumentException("AvailableTo is mandatory");
        }
        if (propertyDonationRequest.getCityId() == 0) {
            throw new IllegalArgumentException("CityId is mandatory");
        }
        if (propertyDonationRequest.getCommunicationTypeId() == 0) {
            throw new IllegalArgumentException("CommunicationTypeId is mandatory");
        }
    }

    public void validateDonateFood(FoodDonationRequest foodDonationRequest) {
        validateMandatoryFields(foodDonationRequest);
        validateFieldsFormat(foodDonationRequest);
        validateExistingEntities(foodDonationRequest);
    }

    private void validateExistingEntities(FoodDonationRequest foodDonationRequest) {
        if (!foodUnitRepository.existsById(foodDonationRequest.getUnit())) {
            throw new EntityNotFoundException(FoodUnit.class,
                            foodDonationRequest.getUnit());
        }
    }

    private void validateFieldsFormat(FoodDonationRequest foodDonationRequest) {
        if (foodDonationRequest.getAmount() < 0) {
            throw new FieldLimitException(ErrorCode.FORMAT_AMOUNT, "Amount",
                            foodDonationRequest.getAmount().toString());
        }
        if (foodDonationRequest.getAmount() > 100000) {
            throw new FieldLimitException(ErrorCode.FORMAT_AMOUNT, "Amount",
                            foodDonationRequest.getAmount().toString());
        }
        if (foodDonationRequest.getUnit() < 0) {
            throw new FieldLimitException(ErrorCode.FORMAT_UNIT, "Unit",
                            foodDonationRequest.getAmount().toString());
        }
        if (!ZonedDateTimeUtils.INSTANCE
                        .isValidZonedDateTime(foodDonationRequest.getExpirationDate())) {
            throw new FormatFieldException(ErrorCode.FORMAT_EXPIRATION_DATE,
                            "Expiration Date", foodDonationRequest.getExpirationDate());
        }
    }

    private void validateMandatoryFields(FoodDonationRequest foodDonationRequest) {
        if (foodDonationRequest.getTitle() == null
                        || !StringUtils.hasText(foodDonationRequest.getTitle())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_TITLE, "Title");
        }
        if (foodDonationRequest.getAmount() == 0) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_AMOUNT, "Amount");
        }
        if (foodDonationRequest.getUnit() == 0) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_AMOUNT, "Unit");
        }
        if (foodDonationRequest.getCommunication() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_COMMUNICATION,
                            "Communication");
        }
        if (foodDonationRequest.getExpirationDate() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_EXPIRATION_DATE,
                            "Expiration Date");
        }
    }
}
