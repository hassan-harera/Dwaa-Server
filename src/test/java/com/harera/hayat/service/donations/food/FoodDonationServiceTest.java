package com.harera.hayat.service.donations.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
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
import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.model.donation.food.FoodDonationResponse;
import com.harera.hayat.model.food.FoodUnit;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.donation.DonationRepository;
import com.harera.hayat.repository.donation.FoodDonationRepository;
import com.harera.hayat.repository.food.FoodUnitRepository;
import com.harera.hayat.service.donation.DonationValidation;
import com.harera.hayat.service.donation.food.FoodDonationService;
import com.harera.hayat.service.donation.food.FoodDonationValidation;
import com.harera.hayat.service.user.auth.AuthService;

@ExtendWith(MockitoExtension.class)
class FoodDonationServiceTest {

    private final int days = 1;
    private FoodDonationService foodDonationService;
    private ModelMapper modelMapper;

    @Mock
    private DonationValidation donationValidation;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private FoodUnitRepository foodUnitRepository;
    @Mock
    private FoodDonationRepository foodDonationRepository;
    @Mock
    private AuthService authService;
    @Mock
    private DonationRepository donationRepository;
    @Mock
    private FoodDonationValidation foodDonationValidation;

    @BeforeEach
    void setUp() {
        modelMapper = new NotNullableMapper();
        foodDonationService = new FoodDonationService(donationRepository,
                        foodDonationValidation, cityRepository, modelMapper, authService,
                        foodUnitRepository, foodDonationRepository, days);
    }

    @Test
    void create_withNotExistedCity_thenThrowEntityNotFoundException() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> {
            foodDonationService.create(request);
        });
    }

    @Test
    void create_withNotExistedFoundUnit_thenThrowEntityNotFoundException() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City()));
        when(foodUnitRepository.findById(request.getUnitId()))
                        .thenReturn(Optional.empty());

        // when
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            foodDonationService.create(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(EntityNotFoundException.class, ex.getClass());
    }

    @Test
    void create_withValidRequest_thenValidateMapping() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));

        City city = new City();
        city.setId(1L);

        FoodUnit foodUnit = new FoodUnit();
        foodUnit.setId(1L);

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(foodUnitRepository.findById(1L)).thenReturn(Optional.of(foodUnit));

        FoodDonationResponse response = foodDonationService.create(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getDonationDate());
        assertNotNull(response.getDonationExpirationDate());
        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getDescription(), response.getDescription());
        assertEquals(request.getCommunicationMethod(), response.getCommunicationMethod());
        assertEquals(request.getCityId(), response.getCity().getId());

        verify(foodDonationValidation, times(1)).validateCreate(request);
        verify(cityRepository, times(1)).findById(1L);
        verify(foodUnitRepository, times(1)).findById(1L);
        verify(foodDonationRepository, times(1)).save(any());
        verify(donationRepository, times(1)).save(any());
    }

    @Test
    void create_withValidRequest_thenVerifyMapping() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setDonationExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City()));
        when(foodUnitRepository.findById(1L)).thenReturn(Optional.of(new FoodUnit()));

        FoodDonationResponse foodDonationResponse = foodDonationService.create(request);
        // then
        assertEquals(request.getTitle(), foodDonationResponse.getTitle());
        assertEquals(request.getDescription(), foodDonationResponse.getDescription());
        assertEquals(request.getCommunicationMethod(),
                        foodDonationResponse.getCommunicationMethod());
        assertEquals(request.getFoodExpirationDate(),
                        foodDonationResponse.getFoodExpirationDate());
        assertEquals(request.getFoodExpirationDate(),
                        foodDonationResponse.getFoodExpirationDate());
        assertEquals(request.getAmount(), foodDonationResponse.getAmount());

        verify(foodDonationValidation, times(1)).validateCreate(request);
        verify(donationRepository, times(1)).save(any());
        verify(foodDonationRepository, times(1)).save(any());
    }
}
