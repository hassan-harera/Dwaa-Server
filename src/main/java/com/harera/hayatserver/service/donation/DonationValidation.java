package com.harera.hayatserver.service.donation;


import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.harera.hayatserver.model.donation.PropertyDonationRequest;
import com.harera.hayatserver.repository.city.CityRepository;
import com.harera.hayatserver.validation.PagingValidation;

@Service
public class DonationValidation {


    @Autowired
    private CityRepository cityRepository;

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
            throw new IllegalArgumentException("Bathrooms number must be less" +
                    " than 10");
        }

        if (propertyDonationRequest.getFloors() > 10) {
            throw new IllegalArgumentException("Floors number must be less " +
                    "than 10");
        }

        if (propertyDonationRequest.getKitchens() > 10) {
            throw new IllegalArgumentException("Kitchens number must be less " +
                    "than 10");
        }

        if (propertyDonationRequest.getAvailableFrom().isAfter(propertyDonationRequest.getAvailableTo())) {
            throw new IllegalArgumentException("Available from must be before " +
                    "available to");
        }

        if (propertyDonationRequest.getAvailableFrom().isBefore(ZonedDateTime.now())) {
            throw new IllegalArgumentException("Available from must be after " +
                    "now");
        }

        if (propertyDonationRequest.getAvailableTo().isBefore(ZonedDateTime.now())) {
            throw new IllegalArgumentException("Available to must be after " +
                    "now");
        }
    }

    private void checkFieldsMandatory(PropertyDonationRequest propertyDonationRequest) {
        if (propertyDonationRequest.getTitle() == null || StringUtils.hasText(propertyDonationRequest.getTitle())) {
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
}