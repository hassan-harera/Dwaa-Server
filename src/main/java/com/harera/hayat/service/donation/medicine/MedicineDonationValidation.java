package com.harera.hayat.service.donation.medicine;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.harera.hayat.common.exception.FieldFormatException;
import com.harera.hayat.common.exception.FieldLimitException;
import com.harera.hayat.common.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.medicine.MedicineRepository;
import com.harera.hayat.repository.medicine.MedicineUnitRepository;
import com.harera.hayat.util.ErrorCode;
import com.harera.hayat.util.FieldFormat;

@Service
public class MedicineDonationValidation {

    private final CityRepository cityRepository;
    private final MedicineUnitRepository medicineUnitRepository;
    private final MedicineRepository medicineRepository;

    public MedicineDonationValidation(CityRepository cityRepository,
                    MedicineUnitRepository medicineUnitRepository,
                    MedicineRepository medicineRepository) {
        this.cityRepository = cityRepository;
        this.medicineUnitRepository = medicineUnitRepository;
        this.medicineRepository = medicineRepository;
    }

    public void validateCreate(MedicineDonationRequest medicineDonationRequest) {
        validateMandatoryFields(medicineDonationRequest);
        validateFieldsFormat(medicineDonationRequest);
    }

    private void validateFieldsFormat(MedicineDonationRequest medicineDonationRequest) {
        //format validation: title (4, 100), amount (1, 100000), medicineExpirationDate (must be after now)
        if (medicineDonationRequest.getTitle().length() < 4
                        || medicineDonationRequest.getTitle().length() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_DONATION_TITLE, "title",
                            FieldFormat.TITLE_PATTERN);
        }
        if (medicineDonationRequest.getAmount() <= 0
                        || medicineDonationRequest.getAmount() > 100000) {
            throw new FieldLimitException(ErrorCode.FORMAT_DONATION_AMOUNT, "amount",
                            String.valueOf(medicineDonationRequest.getAmount()));
        }
        if (medicineDonationRequest.getMedicineExpirationDate()
                        .isBefore(ZonedDateTime.now())) {
            throw new FieldLimitException(ErrorCode.FORMAT_DONATION_EXPIRATION_DATE,
                            "medicine expiration date", medicineDonationRequest
                                            .getMedicineExpirationDate().toString());
        }
    }

    private void validateMandatoryFields(
                    MedicineDonationRequest medicineDonationRequest) {
        if (medicineDonationRequest.getTitle() == null
                        || !StringUtils.hasText(medicineDonationRequest.getTitle())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_DONATION_TITLE,
                            "title");
        }
        if (medicineDonationRequest.getCommunicationMethod() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_DONATION_COMMUNICATION_METHOD,
                            "communication method");
        }
        if (medicineDonationRequest.getMedicineExpirationDate() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_DONATION_EXPIRATION_DATE,
                            "expiration date");
        }
    }
}
