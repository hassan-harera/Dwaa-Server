package com.harera.hayat.donation.service.donation.medicine;

import java.time.OffsetDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.harera.hayat.common.exception.EntityNotFoundException;
import com.harera.hayat.common.model.city.City;
import com.harera.hayat.common.model.medicine.Medicine;
import com.harera.hayat.common.model.medicine.MedicineUnit;
import com.harera.hayat.common.model.user.User;
import com.harera.hayat.common.repository.city.CityRepository;
import com.harera.hayat.common.repository.medicine.MedicineRepository;
import com.harera.hayat.common.repository.medicine.MedicineUnitRepository;
import com.harera.hayat.common.service.user.auth.JwtService;
import com.harera.hayat.donation.model.donation.Donation;
import com.harera.hayat.donation.model.donation.DonationCategory;
import com.harera.hayat.donation.model.donation.medicine.MedicineDonation;
import com.harera.hayat.donation.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.donation.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayat.donation.repository.donation.medicine.MedicineDonationRepository;

import io.jsonwebtoken.JwtException;

@Service
public class MedicineDonationService {

    private final MedicineDonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final MedicineUnitRepository medicineUnitRepository;
    private final ModelMapper modelMapper;
    private final MedicineDonationRepository medicineDonationRepository;
    private final MedicineRepository medicineRepository;
    private final JwtService jwtService;

    public MedicineDonationService(MedicineDonationValidation donationValidation,
                    CityRepository cityRepository,
                    MedicineUnitRepository medicineUnitRepository,
                    ModelMapper modelMapper,
                    MedicineDonationRepository medicineDonationRepository,
                    MedicineRepository medicineRepository, JwtService jwtService) {
        this.donationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.medicineUnitRepository = medicineUnitRepository;
        this.modelMapper = modelMapper;
        this.medicineDonationRepository = medicineDonationRepository;
        this.medicineRepository = medicineRepository;
        this.jwtService = jwtService;
    }

    public MedicineDonationResponse create(
                    MedicineDonationRequest medicineDonationRequest) {
        donationValidation.validateCreate(medicineDonationRequest);

        Donation donation = modelMapper.map(medicineDonationRequest, Donation.class);
        donation.setCategory(DonationCategory.MEDICINE);
        donation.setCity(getCity(medicineDonationRequest.getCityId()));
        donation.setDonationDate(OffsetDateTime.now());
        donation.setUser(jwtService.getRequestUser());

        MedicineDonation medicineDonation =
                        modelMapper.map(medicineDonationRequest, MedicineDonation.class);
        medicineDonation.setMedicineUnit(getUnit(medicineDonationRequest.getUnitId()));
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
}
