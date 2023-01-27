package com.harera.hayat.core.service.donation.medicine;

import java.time.OffsetDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.harera.hayat.core.exception.EntityNotFoundException;
import com.harera.hayat.core.model.city.City;
import com.harera.hayat.core.model.donation.Donation;
import com.harera.hayat.core.model.donation.DonationCategory;
import com.harera.hayat.core.model.donation.medicine.MedicineDonation;
import com.harera.hayat.core.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.core.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayat.core.model.medicine.Medicine;
import com.harera.hayat.core.model.medicine.unit.MedicineUnit;
import com.harera.hayat.core.model.user.User;
import com.harera.hayat.core.repository.city.CityRepository;
import com.harera.hayat.core.repository.donation.DonationRepository;
import com.harera.hayat.core.repository.donation.medicine.MedicineDonationRepository;
import com.harera.hayat.core.repository.medicine.MedicineRepository;
import com.harera.hayat.core.repository.medicine.MedicineUnitRepository;
import com.harera.hayat.core.service.user.auth.AuthService;

import io.jsonwebtoken.JwtException;

@Service
public class MedicineDonationService {

    private final DonationRepository donationRepository;
    private final com.harera.hayat.core.service.donation.medicine.MedicineDonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final MedicineUnitRepository medicineUnitRepository;
    private final ModelMapper modelMapper;
    private final MedicineDonationRepository medicineDonationRepository;
    private final MedicineRepository medicineRepository;
    private final AuthService authService;

    public MedicineDonationService(DonationRepository donationRepository,
                    com.harera.hayat.core.service.donation.medicine.MedicineDonationValidation donationValidation,
                    CityRepository cityRepository,
                    MedicineUnitRepository medicineUnitRepository,
                    ModelMapper modelMapper,
                    MedicineDonationRepository medicineDonationRepository,
                    MedicineRepository medicineRepository, AuthService authService) {
        this.donationRepository = donationRepository;
        this.donationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.medicineUnitRepository = medicineUnitRepository;
        this.modelMapper = modelMapper;
        this.medicineDonationRepository = medicineDonationRepository;
        this.medicineRepository = medicineRepository;
        this.authService = authService;
    }

    public MedicineDonationResponse create(
                    MedicineDonationRequest medicineDonationRequest) {
        donationValidation.validateCreate(medicineDonationRequest);

        Donation donation = modelMapper.map(medicineDonationRequest, Donation.class);
        donation.setCategory(DonationCategory.MEDICINE);
        donation.setCity(getCity(medicineDonationRequest.getCityId()));
        donation.setDonationDate(OffsetDateTime.now());
        donation.setUser(authService.getRequestUser());

        Donation savedDonation = donationRepository.save(donation);

        MedicineDonation medicineDonation =
                        modelMapper.map(medicineDonationRequest, MedicineDonation.class);
        medicineDonation.setMedicineUnit(getUnit(medicineDonationRequest.getUnitId()));
        medicineDonation.setDonation(savedDonation);
        medicineDonation.setMedicine(
                        getMedicine(medicineDonationRequest.getMedicineId()));
        medicineDonationRepository.save(medicineDonation);
        MedicineDonationResponse response =
                        modelMapper.map(medicineDonation, MedicineDonationResponse.class);
        modelMapper.map(donation, response);
        return response;
    }

    private Medicine getMedicine(long medicineId) {
        return medicineRepository.findById(medicineId).orElseThrow(
                        () -> new EntityNotFoundException(Medicine.class, medicineId));
    }

    private MedicineUnit getUnit(long unitId) {
        return medicineUnitRepository.findById(unitId).orElseThrow(
                        () -> new EntityNotFoundException(MedicineUnit.class, unitId));
    }

    private City getCity(long cityId) {
        return cityRepository.findById(cityId).orElseThrow(
                        () -> new EntityNotFoundException(City.class, cityId));
    }

    private User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        if (principal instanceof User user)
            return user;
        throw new JwtException("Invalid token");
    }

    public void deactivate(Donation donation) {
        MedicineDonation medicineDonation =
                        medicineDonationRepository.findByDonationId(donation.getId())
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                        MedicineDonation.class,
                                                        donation.getId()));
        medicineDonation.deactivate();
        medicineDonationRepository.save(medicineDonation);
    }
}
