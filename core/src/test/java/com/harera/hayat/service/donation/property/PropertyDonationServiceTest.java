package com.harera.hayat.service.donation.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.donation.CommunicationMethod;
import com.harera.hayat.model.donation.DonationCategory;
import com.harera.hayat.model.donation.DonationState;
import com.harera.hayat.model.donation.property.PropertyDonation;
import com.harera.hayat.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.model.donation.property.PropertyDonationResponse;
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

    @Test
    void create_thenValidateResponse() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(1);
        propertyDonationRequest.setPeopleCapacity(1);

        City city = new City();
        city.setId(1L);

        // when
        when(cityRepository.findById(propertyDonationRequest.getCityId()))
                        .thenReturn(Optional.of(city));

        PropertyDonationResponse propertyDonationResponse =
                        propertyDonationService.create(propertyDonationRequest);

        // then
        assertEquals(propertyDonationRequest.getTitle(),
                        propertyDonationResponse.getTitle());
        assertEquals(propertyDonationRequest.getCommunicationMethod(),
                        propertyDonationResponse.getCommunicationMethod());
        assertEquals(propertyDonationRequest.getCityId(),
                        propertyDonationResponse.getCity().getId());
        assertEquals(propertyDonationRequest.getRooms(),
                        propertyDonationResponse.getRooms());
        assertEquals(propertyDonationRequest.getBathrooms(),
                        propertyDonationResponse.getBathrooms());
        assertEquals(propertyDonationRequest.getKitchens(),
                        propertyDonationResponse.getKitchens());
        assertEquals(DonationCategory.PROPERTY, propertyDonationResponse.getCategory());
        assertEquals(DonationState.PENDING, propertyDonationResponse.getState());

        verify(propertyDonationValidation).validateCreate(propertyDonationRequest);
        verify(propertyDonationRepository).save(any());
    }

    @Test
    void get_withNotExistedPropertyDonation_shouldThrowEntityNotFoundException() {
        // given
        Long propertyDonationId = 1L;

        PropertyDonation propertyDonation = new PropertyDonation();
        propertyDonation.setId(propertyDonationId);
        propertyDonation.setTitle("title");
        propertyDonation.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonation.setRooms(1);
        propertyDonation.setBathrooms(1);
        propertyDonation.setKitchens(1);
        propertyDonation.setPeopleCapacity(1);

        // when
        when(propertyDonationRepository.findById(propertyDonationId))
                        .thenReturn(Optional.empty());

        EntityNotFoundException entityNotFoundException = assertThrows(
                        EntityNotFoundException.class,
                        () -> propertyDonationService.get(propertyDonationId));
        // then
        assertEquals(ErrorCode.NOT_FOUND_PROPERTY_DONATION,
                        entityNotFoundException.getCode());
    }

    @Test
    void get_thenValidateResponse() {
        // given
        Long propertyDonationId = 1L;

        City city = new City();
        city.setId(1L);

        PropertyDonation propertyDonation = new PropertyDonation();
        propertyDonation.setId(propertyDonationId);
        propertyDonation.setCity(city);
        propertyDonation.setTitle("title");
        propertyDonation.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonation.setRooms(1);
        propertyDonation.setBathrooms(1);
        propertyDonation.setKitchens(1);
        propertyDonation.setPeopleCapacity(1);
        propertyDonation.setCategory(DonationCategory.PROPERTY);
        propertyDonation.setState(DonationState.PENDING);

        // when
        when(propertyDonationRepository.findById(propertyDonationId))
                        .thenReturn(Optional.of(propertyDonation));

        PropertyDonationResponse propertyDonationResponse =
                        propertyDonationService.get(propertyDonationId);

        // then
        assertEquals(propertyDonation.getTitle(), propertyDonationResponse.getTitle());
        assertEquals(propertyDonation.getCommunicationMethod(),
                        propertyDonationResponse.getCommunicationMethod());
        assertEquals(propertyDonation.getCity().getId(),
                        propertyDonationResponse.getCity().getId());
        assertEquals(propertyDonation.getRooms(), propertyDonationResponse.getRooms());
        assertEquals(propertyDonation.getBathrooms(),
                        propertyDonationResponse.getBathrooms());
        assertEquals(propertyDonation.getKitchens(),
                        propertyDonationResponse.getKitchens());
        assertEquals(DonationCategory.PROPERTY, propertyDonationResponse.getCategory());
        assertEquals(DonationState.PENDING, propertyDonationResponse.getState());
    }
}
