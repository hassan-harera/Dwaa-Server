package com.harera.hayat.service.donation.property;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.service.donation.DonationValidation;
import com.harera.hayat.util.ErrorCode;

@Service
public class PropertyDonationValidation extends DonationValidation {

    public void validateCreate(PropertyDonationRequest propertyDonationRequest) {
        validateDonation(propertyDonationRequest);
        validateMandatory(propertyDonationRequest);
        validateFormat(propertyDonationRequest);
    }

    private void validateMandatory(PropertyDonationRequest propertyDonationRequest) {
        if (propertyDonationRequest.getRooms() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_PROPERTY_DONATION_ROOMS,
                            "rooms");
        }
        if (propertyDonationRequest.getBathrooms() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_PROPERTY_DONATION_BATH_ROOMS,
                            "bathrooms");
        }
        if (propertyDonationRequest.getKitchens() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_PROPERTY_DONATION_KITCHENS, "kitchens");
        }
        if (propertyDonationRequest.getPeopleCapacity() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_PROPERTY_DONATION_PEOPLE_CAPACITY,
                            "people_capacity");
        }
        if (propertyDonationRequest.getAvailableFrom() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_PROPERTY_DONATION_AVAILABLE_FROM,
                            "available_from");
        }
        if (propertyDonationRequest.getAvailableTo() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_PROPERTY_DONATION_AVAILABLE_TO,
                            "available_to");
        }
    }

    private void validateFormat(PropertyDonationRequest propertyDonationRequest) {
        if (propertyDonationRequest.getRooms() < 1
                        || propertyDonationRequest.getRooms() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_PROPERTY_DONATION_ROOMS,
                            "rooms");
        }

        if (propertyDonationRequest.getBathrooms() < 1
                        || propertyDonationRequest.getBathrooms() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_PROPERTY_DONATION_BATH_ROOMS,
                            "bathrooms");
        }

        if (propertyDonationRequest.getKitchens() < 1
                        || propertyDonationRequest.getKitchens() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_PROPERTY_DONATION_KITCHENS,
                            "kitchens");
        }

        if (propertyDonationRequest.getPeopleCapacity() < 1
                        || propertyDonationRequest.getPeopleCapacity() > 1000) {
            throw new FieldFormatException(
                            ErrorCode.FORMAT_PROPERTY_DONATION_PEOPLE_CAPACITY,
                            "people_capacity");
        }

        if (propertyDonationRequest.getAvailableFrom().isBefore(OffsetDateTime.now())) {
            throw new FieldFormatException(
                            ErrorCode.FORMAT_PROPERTY_DONATION_AVAILABLE_FROM,
                            "available_from");
        }

        if (propertyDonationRequest.getAvailableTo()
                        .isBefore(OffsetDateTime.now().plusDays(1))) {
            throw new FieldFormatException(
                            ErrorCode.FORMAT_PROPERTY_DONATION_AVAILABLE_TO,
                            "available_to");
        }
    }
}
