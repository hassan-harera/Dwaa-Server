package com.harera.hayat.service.donation.medicine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import com.harera.hayat.service.user.auth.AuthService;

@ExtendWith(MockitoExtension.class)
class MedicineDonationServiceTest {

    private MedicineDonationService medicineDonationService;
    private ModelMapper modelMapper;

    @Mock
    private DonationRepository donationRepository;
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

    @BeforeEach
    void setUp() {
        MedicineDonationValidation donationValidation = new MedicineDonationValidation(
                        cityRepository, medicineUnitRepository, medicineRepository);
        modelMapper = new NotNullableMapper();
        medicineDonationService = new MedicineDonationService(donationRepository,
                        donationValidation, cityRepository, medicineUnitRepository,
                        modelMapper, medicineDonationRepository, medicineRepository,
                        authService);
    }

    @Test
    void create_withValidRequest_thenVerifyPersistenceCall() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setExpirationDate(ZonedDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(ZonedDateTime.now().plusMonths(1));

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
    void create_with_then() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(ZonedDateTime.now());
        request.setMedicineId(1L);
        request.setUnitId(1L);
        request.setAmount(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setExpirationDate(ZonedDateTime.now().plusMonths(1));

        // when
        // then
    }

}
