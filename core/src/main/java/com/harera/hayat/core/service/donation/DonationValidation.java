package com.harera.hayat.core.service.donation;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.harera.hayat.core.exception.FieldFormatException;
import com.harera.hayat.core.exception.MandatoryFieldException;
import com.harera.hayat.core.model.donation.DonationDto;
import com.harera.hayat.core.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.core.repository.city.CityRepository;
import com.harera.hayat.core.util.ErrorCode;
import com.harera.hayat.core.util.FieldFormat;
import com.harera.hayat.core.validation.PagingValidation;

@Service
public class DonationValidation {

    private final CityRepository cityRepository;

    public DonationValidation(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

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

        if (propertyDonationRequest.getAvailableFrom().isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Available from must be after " + "now");
        }

        if (propertyDonationRequest.getAvailableTo().isBefore(OffsetDateTime.now())) {
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

    public void validate(DonationDto donationDto) {
        validateMandatory(donationDto);
        validateFormat(donationDto);
    }

    private void validateFormat(DonationDto donationDto) {
        if (donationDto.getTitle().length() < 4
                        || donationDto.getTitle().length() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_DONATION_TITLE, "title",
                            FieldFormat.TITLE_PATTERN);
        }
    }

    private void validateMandatory(DonationDto donationDto) {
        if (isBlank(donationDto.getTitle())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_DONATION_TITLE,
                            "title");
        }
        if (donationDto.getCommunicationMethod() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_DONATION_COMMUNICATION_METHOD,
                            "communication method");
        }
    }
}
