package com.harera.hayat.donation.service.donation.property;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.harera.hayat.common.exception.DocumentNotFoundException;
import com.harera.hayat.common.exception.EntityNotFoundException;
import com.harera.hayat.common.model.city.City;
import com.harera.hayat.common.model.user.User;
import com.harera.hayat.common.repository.city.CityRepository;
import com.harera.hayat.common.util.ErrorCode;
import com.harera.hayat.donation.model.donation.DonationCategory;
import com.harera.hayat.donation.model.donation.DonationState;
import com.harera.hayat.donation.model.donation.property.PropertyDonation;
import com.harera.hayat.donation.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.donation.model.donation.property.PropertyDonationResponse;
import com.harera.hayat.donation.repository.donation.property.PropertyDonationRepository;

import io.jsonwebtoken.JwtException;

@Service
public class PropertyDonationService {

    private final PropertyDonationRepository propertyDonationRepository;
    private final PropertyDonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public PropertyDonationService(PropertyDonationRepository propertyDonationRepository,
                    PropertyDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper) {
        this.propertyDonationRepository = propertyDonationRepository;
        this.donationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public PropertyDonationResponse create(
                    PropertyDonationRequest propertyDonationRequest) {
        donationValidation.validateCreate(propertyDonationRequest);
        PropertyDonation propertyDonation =
                        modelMapper.map(propertyDonationRequest, PropertyDonation.class);
        assignCity(propertyDonation, propertyDonationRequest.getCityId());
        propertyDonation.setUser(getRequestUser());
        propertyDonation.setState(DonationState.PENDING);
        propertyDonation.setCategory(DonationCategory.PROPERTY);
        propertyDonationRepository.save(propertyDonation);
        return modelMapper.map(propertyDonation, PropertyDonationResponse.class);
    }

    public User getRequestUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        if (principal instanceof User user)
            return user;
        throw new JwtException("Invalid token");
    }
    private void assignCity(PropertyDonation donation, Long cityId) {
        City city = cityRepository.findById(cityId)
                        .orElseThrow(() -> new EntityNotFoundException(City.class, cityId,
                                        ErrorCode.NOT_FOUND_DONATION_CITY));
        donation.setCity(city);
    }

    public PropertyDonationResponse get(Long propertyDonationId) {
        PropertyDonation propertyDonation = propertyDonationRepository
                        .findById(propertyDonationId)
                        .orElseThrow(() -> new DocumentNotFoundException(
                                        PropertyDonation.class, propertyDonationId,
                                        ErrorCode.NOT_FOUND_PROPERTY_DONATION));
        return modelMapper.map(propertyDonation, PropertyDonationResponse.class);
    }
}
