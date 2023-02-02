package com.harera.hayat.service.donation.property;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.donation.property.PropertyDonation;
import com.harera.hayat.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.model.donation.property.PropertyDonationResponse;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.donation.property.PropertyDonationRepository;
import com.harera.hayat.service.user.auth.AuthService;
import com.harera.hayat.util.ErrorCode;

@Service
public class PropertyDonationService {

    private final AuthService authService;
    private final PropertyDonationRepository propertyDonationRepository;
    private final PropertyDonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public PropertyDonationService(AuthService authService,
                    PropertyDonationRepository propertyDonationRepository,
                    PropertyDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper) {
        this.authService = authService;
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
        assignUser(propertyDonation);
        propertyDonationRepository.save(propertyDonation);
        return modelMapper.map(propertyDonation, PropertyDonationResponse.class);
    }

    private void assignUser(PropertyDonation propertyDonation) {
        propertyDonation.setUser(authService.getRequestUser());
    }

    private void assignCity(PropertyDonation donation, Long cityId) {
        City city = cityRepository.findById(cityId)
                        .orElseThrow(() -> new EntityNotFoundException(City.class, cityId,
                                        ErrorCode.NOT_FOUND_DONATION_CITY));
        donation.setCity(city);
    }
}
