package com.harera.hayat.service.need;

import java.time.OffsetDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.need.Need;
import com.harera.hayat.model.need.NeedCategory;
import com.harera.hayat.model.need.medicine.MedicineNeed;
import com.harera.hayat.model.need.medicine.MedicineNeedRequest;
import com.harera.hayat.model.need.medicine.MedicineNeedResponse;
import com.harera.hayat.repository.need.NeedRepository;
import com.harera.hayat.repository.need.NeedStatus;
import com.harera.hayat.repository.need.medicine.MedicineNeedRepository;
import com.harera.hayat.repository.user.auth.TokenRepository;
import com.harera.hayat.service.city.CityService;
import com.harera.hayat.service.donation.medicine.MedicineUnitService;
import com.harera.hayat.service.medicine.MedicineService;
import com.harera.hayat.service.user.UserService;

@Service
public class MedicineNeedService {

    private final MedicineNeedRepository medicineNeedRepository;
    private final NeedRepository needRepository;
    private final MedicineNeedValidation medicineNeedValidation;
    private final ModelMapper modelMapper;
    private final CityService cityService;
    private final MedicineService medicineService;

    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final int medicineNeedExpirationDays;
    private final MedicineUnitService medicineUnitService;

    public MedicineNeedService(MedicineNeedRepository medicineNeedRepository,
                    NeedRepository needRepository,
                    MedicineNeedValidation medicineNeedValidation,
                    ModelMapper modelMapper, CityService cityService,
                    MedicineService medicineService, UserService userService,
                    TokenRepository tokenRepository,
                    @Value("${needs.medicine.expiration_days}") String medicineNeedExpirationDays,
                    MedicineUnitService medicineUnitService) {
        this.medicineNeedRepository = medicineNeedRepository;
        this.needRepository = needRepository;
        this.medicineNeedValidation = medicineNeedValidation;
        this.modelMapper = modelMapper;
        this.cityService = cityService;
        this.medicineService = medicineService;
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.medicineNeedExpirationDays = Integer.parseInt(medicineNeedExpirationDays);
        this.medicineUnitService = medicineUnitService;
    }

    public MedicineNeedResponse create(MedicineNeedRequest medicineNeedRequest,
                    String token) {
        medicineNeedValidation.validate(medicineNeedRequest);

        var need = modelMapper.map(medicineNeedRequest, Need.class);
        need.setNeedDate(OffsetDateTime.now());
        need.setNeedExpirationDate(
                        OffsetDateTime.now().plusDays(medicineNeedExpirationDays));
        need.setCity(cityService.getCity(medicineNeedRequest.getCityId()));
        need.setCategory(NeedCategory.MEDICINE);
        need.setUser(userService.getUser(tokenRepository.getUserIdForToken(token)));
        need.setStatus(NeedStatus.PENDING);
        need = needRepository.save(need);

        var medicineNeed = modelMapper.map(medicineNeedRequest, MedicineNeed.class);
        medicineNeed.setMedicine(
                        medicineService.getMedicine(medicineNeedRequest.getMedicineId()));
        medicineNeed.setUnit(medicineUnitService
                        .getMedicineUnit(medicineNeedRequest.getMedicineUnitId()));
        medicineNeed.setNeed(need);
        medicineNeed = medicineNeedRepository.save(medicineNeed);

        MedicineNeedResponse response = modelMapper.map(need, MedicineNeedResponse.class);
        modelMapper.map(medicineNeed, response);

        return response;
    }

    /**
     * Flowchart for this method:
     * Start
     * MedicineNeedService#get
     * MedicineNeedRepository
     * #findByIdOrElseThrow
     * EntityNotFoundException
     * map medicineNeed
     * #need to
     * MedicineNeedResponse
     * map medicineNeed
     * to medicineNeedResponse
     * map medicineNeed
     * to medicineNeedResponse
     * return
     * medicineNeedResponse
     * End
     */
    public MedicineNeedResponse get(Long medicineNeedId) {
        var medicineNeed = medicineNeedRepository.findById(medicineNeedId)
                        .orElseThrow(() -> new EntityNotFoundException(MedicineNeed.class,
                                        medicineNeedId));
        MedicineNeedResponse medicineNeedResponse = modelMapper
                        .map(medicineNeed.getNeed(), MedicineNeedResponse.class);
        modelMapper.map(medicineNeed, medicineNeedResponse);
        return medicineNeedResponse;

    }
}
