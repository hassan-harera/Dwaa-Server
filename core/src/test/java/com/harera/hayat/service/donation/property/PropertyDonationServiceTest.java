package com.harera.hayat.service.donation.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.harera.hayat.config.NotNullableMapper;
import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.donation.DonationRepository;
import com.harera.hayat.repository.donation.property.PropertyDonationRepository;
import com.harera.hayat.service.user.auth.AuthService;
import com.harera.hayat.util.ErrorCode;

@ExtendWith(MockitoExtension.class)
class PropertyDonationServiceTest {

    private final ModelMapper modelMapper = new NotNullableMapper();

    @Mock
    private CityRepository cityRepository;
    @Mock
    private DonationRepository donationRepository;
    @Mock
    private PropertyDonationValidation propertyDonationValidation;
    @Mock
    private PropertyDonationRepository propertyDonationRepository;
    @Mock
    private AuthService authService;

    private PropertyDonationService propertyDonationService;

    @BeforeEach
    public void setUp() {
        propertyDonationService = new PropertyDonationService(authService,
                        propertyDonationRepository, propertyDonationValidation,
                        cityRepository, modelMapper);
    }

    /**
     * Test the following flow chart:
     * Start
     * PropertyDonationService#create
     * PropertyDonationValidation
     * #validateCreate
     * map request to PropertyDonation
     * set city from cityId
     * set user from request authorization
     * save entity
     * map saved propertyDonation to
     * PropertyDonationResponse
     * End
     */

    @Test
    void create_withNotExistedCityId_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setCityId(1L);

        // when
        when(cityRepository.findById(propertyDonationRequest.getCityId()))
                        .thenReturn(Optional.empty());

        EntityNotFoundException entityNotFoundException = assertThrows(
                        EntityNotFoundException.class,
                        () -> propertyDonationService.create(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.NOT_FOUND_DONATION_CITY,
                        entityNotFoundException.getCode());
        verify(propertyDonationValidation).validateCreate(propertyDonationRequest);
    }
}
