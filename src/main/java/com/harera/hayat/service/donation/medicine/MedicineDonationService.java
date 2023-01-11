package com.harera.hayat.service.donation.medicine;

import java.time.OffsetDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.DonationCategory;
import com.harera.hayat.model.donation.medicine.MedicineDonation;
import com.harera.hayat.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.model.donation.medicine.MedicineDonationResponse;
import com.harera.hayat.model.medicine.Medicine;
import com.harera.hayat.model.medicine.unit.MedicineUnit;
import com.harera.hayat.model.user.User;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.donation.DonationRepository;
import com.harera.hayat.repository.donation.medicine.MedicineDonationRepository;
import com.harera.hayat.repository.medicine.MedicineRepository;
import com.harera.hayat.repository.medicine.MedicineUnitRepository;
import com.harera.hayat.service.FileManager;
import com.harera.hayat.service.donation.DonationImagesService;
import com.harera.hayat.service.user.auth.AuthService;

import io.jsonwebtoken.JwtException;

@Service
public class MedicineDonationService {

    private final DonationRepository donationRepository;
    private final MedicineDonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final MedicineUnitRepository medicineUnitRepository;
    private final ModelMapper modelMapper;
    private final MedicineDonationRepository medicineDonationRepository;
    private final MedicineRepository medicineRepository;
    private final AuthService authService;
    private final DonationImagesService donationImagesService;

    private final Integer pageSizeOfMedicineDonation;

    public MedicineDonationService(DonationRepository donationRepository,
                    MedicineDonationValidation donationValidation,
                    CityRepository cityRepository,
                    MedicineUnitRepository medicineUnitRepository,
                    ModelMapper modelMapper,
                    MedicineDonationRepository medicineDonationRepository,
                    MedicineRepository medicineRepository, AuthService authService,
                    FileManager fileManager,
                    @Value(value = "${donations.medicine.page_size}") String pageSizeOfMedicineDonation,
                    DonationImagesService donationImagesService) {
        this.donationRepository = donationRepository;
        this.donationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.medicineUnitRepository = medicineUnitRepository;
        this.modelMapper = modelMapper;
        this.medicineDonationRepository = medicineDonationRepository;
        this.medicineRepository = medicineRepository;
        this.authService = authService;
        this.pageSizeOfMedicineDonation = Integer.valueOf(pageSizeOfMedicineDonation);
        this.donationImagesService = donationImagesService;
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
        medicineDonation.setUnit(getUnit(medicineDonationRequest.getUnitId()));
        medicineDonation.setDonation(savedDonation);
        medicineDonation.setMedicine(
                        getMedicine(medicineDonationRequest.getMedicineId()));
        medicineDonationRepository.save(medicineDonation);
        MedicineDonationResponse response =
                        modelMapper.map(medicineDonation, MedicineDonationResponse.class);
        modelMapper.map(donation, response);
        return response;
    }

    public List<MedicineDonationResponse> list(Integer page) {
        page = Integer.max(page, 1) - 1;
        List<MedicineDonation> medicineDonationList =
                        medicineDonationRepository.findAllActiveMedicineDonation(
                                        Pageable.ofSize(pageSizeOfMedicineDonation)
                                                        .withPage(page));

        return medicineDonationList.stream().map(medicineDonation -> {
            MedicineDonationResponse donationResponse =
                            modelMapper.map(medicineDonation.getDonation(),
                                            MedicineDonationResponse.class);
            modelMapper.map(medicineDonation, donationResponse);
            return donationResponse;
        }).toList();
    }

    public MedicineDonationResponse get(Long id) {
        MedicineDonation medicineDonation = medicineDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        MedicineDonation.class, id));
        return modelMapper.map(medicineDonation, MedicineDonationResponse.class);
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

    public MedicineDonationResponse insertImage(Long id, MultipartFile image) {
        MedicineDonation medicineDonation = medicineDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        MedicineDonation.class, id));
        Donation donation = medicineDonation.getDonation();
        donation = donationImagesService.insertImage(donation, image);
        medicineDonation.setDonation(donation);
        return modelMapper.map(medicineDonation, MedicineDonationResponse.class);
    }
}
