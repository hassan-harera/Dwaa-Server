package com.harera.hayat.service.donation.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
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
import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.food.FoodDonation;
import com.harera.hayat.model.donation.food.FoodDonationRequest;
import com.harera.hayat.model.donation.food.FoodDonationResponse;
import com.harera.hayat.model.donation.food.FoodDonationUpdateRequest;
import com.harera.hayat.model.food.FoodUnit;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.donation.DonationRepository;
import com.harera.hayat.repository.donation.food.FoodDonationRepository;
import com.harera.hayat.repository.donation.image.DonationImageRepository;
import com.harera.hayat.repository.food.FoodUnitRepository;
import com.harera.hayat.service.FileManager;
import com.harera.hayat.service.donation.DonationValidation;
import com.harera.hayat.service.file.CloudFileService;
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
    @Mock
    private DonationImageRepository donationImageRepository;
    @Mock
    private CloudFileService cloudFileService;
    @Mock
    private FileManager fileManager;

    @BeforeEach
    void setUp() {
        modelMapper = new NotNullableMapper();
        foodDonationService = new FoodDonationService(donationRepository,
                        foodDonationValidation, cityRepository, modelMapper, authService,
                        foodUnitRepository, foodDonationRepository, days, fileManager,
                        cloudFileService, donationImageRepository);
    }

    @Test
    void create_withNotExistedCity_thenThrowEntityNotFoundException() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

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
        request.setDonationDate(OffsetDateTime.now());
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

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
        request.setDonationDate(OffsetDateTime.now());
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

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
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setDonationExpirationDate(OffsetDateTime.now().plusMonths(1));

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

    @Test
    void update_withNotExistedFoodDonation_thenThrowEntityNotFoundException() {
        // given
        Long id = 1L;

        FoodDonationUpdateRequest request = new FoodDonationUpdateRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        when(foodDonationRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> {
            foodDonationService.update(id, request);
        });
    }

    @Test
    void update_withNotExistedCity_thenThrowEntityNotFoundException() {
        // given
        Long id = 1L;

        FoodDonationUpdateRequest request = new FoodDonationUpdateRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        Donation donation = new Donation();
        donation.setId(1L);

        FoodDonation foodDonation = new FoodDonation();
        foodDonation.setId(1L);
        foodDonation.setDonation(donation);

        // when
        when(foodDonationRepository.findById(id)).thenReturn(Optional.of(foodDonation));

        // then
        assertThrows(EntityNotFoundException.class, () -> {
            foodDonationService.update(id, request);
        });
    }

    @Test
    void update_withNotExistedFoundUnit_thenThrowEntityNotFoundException() {
        // given
        Long id = 1L;

        FoodDonationUpdateRequest request = new FoodDonationUpdateRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        Donation donation = new Donation();
        donation.setId(1L);

        FoodDonation foodDonation = new FoodDonation();
        foodDonation.setId(1L);
        foodDonation.setDonation(donation);

        // when
        when(foodDonationRepository.findById(id)).thenReturn(Optional.of(foodDonation));
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City()));

        // when
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            foodDonationService.update(id, request);
        });

        // then
        assertNotNull(ex);
        assertEquals(EntityNotFoundException.class, ex.getClass());
    }

    @Test
    void update_withValidRequest_thenValidateMapping() {
        // given
        Long id = 1L;

        FoodDonationUpdateRequest request = new FoodDonationUpdateRequest();
        request.setCityId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        City city = new City();
        city.setId(1L);

        FoodUnit foodUnit = new FoodUnit();
        foodUnit.setId(1L);

        Donation donation = new Donation();
        donation.setId(1L);

        FoodDonation foodDonation = new FoodDonation();
        foodDonation.setId(1L);
        foodDonation.setDonation(donation);

        // when
        when(foodDonationRepository.findById(id)).thenReturn(Optional.of(foodDonation));
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(foodUnitRepository.findById(1L)).thenReturn(Optional.of(foodUnit));

        FoodDonationResponse response = foodDonationService.update(id, request);

        // then
        assertNotNull(response);
        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getDescription(), response.getDescription());
        assertEquals(request.getCommunicationMethod(), response.getCommunicationMethod());
        assertEquals(request.getCityId(), response.getCity().getId());

        verify(foodDonationValidation, times(1)).validateUpdate(id, request);
        verify(cityRepository, times(1)).findById(1L);
        verify(foodUnitRepository, times(1)).findById(1L);
        verify(foodDonationRepository, times(1)).save(any());
        verify(donationRepository, times(1)).save(any());
    }

    @Test
    void get_withNotExistedFoodDonation_thenThrowEntityNotFoundException() {
        // given
        Long id = 1L;

        // when
        when(foodDonationRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> foodDonationService.get(id));
    }

    @Test
    void get_thenValidateMapping() {
        // given
        Long id = 1L;

        City city = new City();
        city.setId(1L);

        FoodUnit foodUnit = new FoodUnit();
        foodUnit.setId(1L);

        Donation donation = new Donation();
        donation.setId(1L);
        donation.setTitle("title");
        donation.setDescription("description");
        donation.setCommunicationMethod(CommunicationMethod.CHAT);
        donation.setCity(city);

        FoodDonation foodDonation = new FoodDonation();
        foodDonation.setAmount(1F);
        foodDonation.setUnit(foodUnit);
        foodDonation.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        foodDonation.setDonation(donation);

        // when
        when(foodDonationRepository.findById(id)).thenReturn(Optional.of(foodDonation));

        FoodDonationResponse response = foodDonationService.get(id);

        // then
        assertNotNull(response);
        assertEquals(donation.getTitle(), response.getTitle());
        assertEquals(donation.getDescription(), response.getDescription());
        assertEquals(donation.getCommunicationMethod(),
                        response.getCommunicationMethod());
        assertEquals(donation.getCity().getId(), response.getCity().getId());

        assertEquals(foodDonation.getUnit().getId(), response.getUnit().getId());
        assertEquals(foodDonation.getAmount(), response.getAmount());
        assertTrue(foodDonation.getFoodExpirationDate()
                        .isEqual(response.getFoodExpirationDate()));

        verify(foodDonationRepository, times(1)).findById(id);
    }
}
