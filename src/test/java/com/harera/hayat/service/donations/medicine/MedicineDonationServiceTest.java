package com.harera.hayat.service.donations.medicine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.harera.hayat.model.donation.medicine.Medicine;
import com.harera.hayat.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayat.model.donation.medicine.MedicineUnit;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.donation.DonationRepository;
import com.harera.hayat.repository.donation.medicine.MedicineDonationRepository;
import com.harera.hayat.repository.medicine.MedicineRepository;
import com.harera.hayat.repository.medicine.MedicineUnitRepository;
import com.harera.hayat.service.donation.DonationValidation;
import com.harera.hayat.service.donation.medicine.MedicineDonationService;
import com.harera.hayat.service.donation.medicine.MedicineDonationValidation;
import com.harera.hayat.service.user.auth.AuthService;

@ExtendWith(MockitoExtension.class)
class MedicineDonationServiceTest {

    private MedicineDonationService medicineDonationService;
    private ModelMapper modelMapper;

    @Mock
    private DonationValidation donationValidation;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private MedicineUnitRepository medicineUnitRepository;
    @Mock
    private MedicineDonationRepository medicineDonationRepository;
    @Mock
    private MedicineRepository medicineRepository;;
    @Mock
    private AuthService authService;
    @Mock
    private DonationRepository donationRepository;

    @BeforeEach
    void setUp() {
        MedicineDonationValidation medicineDonationValidation =
                        new MedicineDonationValidation(donationValidation);
        modelMapper = new NotNullableMapper();
        medicineDonationService = new MedicineDonationService(donationRepository,
                        medicineDonationValidation, cityRepository,
                        medicineUnitRepository,
                        modelMapper, medicineDonationRepository, medicineRepository,
                        authService);
    }

    @Test
    void create_withValidRequest_thenVerifyPersistenceCall() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));

        City city = new City();
        city.setId(1L);

        Medicine medicine = new Medicine();
        medicine.setId(1L);

        MedicineUnit medicineUnit = new MedicineUnit();
        medicineUnit.setId(1L);

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));
        when(medicineUnitRepository.findById(1L)).thenReturn(Optional.of(medicineUnit));

        MedicineDonationResponse response = medicineDonationService.create(request);

        // then
        medicineDonationRepository.save(any());
        donationRepository.save(any());

        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getDescription(), response.getDescription());
    }

    @Test
    void create_withNotFoundUnit_thenEntityNotFoundException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City()));
        when(medicineUnitRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class,
                        () -> medicineDonationService.create(request));
    }

    @Test
    void create_withNotFoundMedicine_thenEntityNotFoundException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City()));
        when(medicineRepository.findById(1L)).thenReturn(Optional.empty());
        when(medicineUnitRepository.findById(1L)).thenReturn(Optional.of(new MedicineUnit()));

        Exception ex = null;
        try {
            medicineDonationService.create(request);
        } catch (Exception e) {
            ex = e;
            
        }

        // then
        assertNotNull(ex);
        assertEquals(EntityNotFoundException.class, ex.getClass());
    }

    @Test
    void create_withNotFoundCity_thenEntityNotFoundException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = null;
        try {
            medicineDonationService.create(request);
        } catch (Exception e) {
            ex = e;
            
        }

        // then
        assertNotNull(ex);
        assertEquals(EntityNotFoundException.class, ex.getClass());
    }

    @Test
    void create_withValidRequest_thenVerifyMapping() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setDonationExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City()));
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(new Medicine()));
        when(medicineUnitRepository.findById(1L))
                        .thenReturn(Optional.of(new MedicineUnit()));

        MedicineDonationResponse medicineDonationResponse =
                        medicineDonationService.create(request);
        // then
        assertEquals(request.getTitle(), medicineDonationResponse.getTitle());
        assertEquals(request.getDescription(), medicineDonationResponse.getDescription());
        assertEquals(request.getCommunicationMethod(),
                        medicineDonationResponse.getCommunicationMethod());
        assertEquals(request.getMedicineExpirationDate(),
                        medicineDonationResponse.getMedicineExpirationDate());
        assertEquals(request.getMedicineExpirationDate(),
                        medicineDonationResponse.getMedicineExpirationDate());
        assertEquals(request.getAmount(), medicineDonationResponse.getAmount());

        verify(donationValidation, times(1)).validate(request);
        verify(medicineDonationRepository, times(1)).save(any());
    }
}
