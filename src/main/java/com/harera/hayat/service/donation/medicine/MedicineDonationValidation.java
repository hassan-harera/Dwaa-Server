package com.harera.hayat.service.donation.medicine;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.FieldLimitException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.service.donation.DonationValidation;
import com.harera.hayat.util.ErrorCode;

@Service
public class MedicineDonationValidation {

    private final DonationValidation donationValidation;

    public MedicineDonationValidation(DonationValidation donationValidation) {
        this.donationValidation = donationValidation;
    }

    public void validateCreate(MedicineDonationRequest medicineDonationRequest) {
        donationValidation.validateCreate(medicineDonationRequest);
        validateMandatoryCreate(medicineDonationRequest);
        validateFormatCreate(medicineDonationRequest);
    }

    private void validateFormatCreate(MedicineDonationRequest medicineDonationRequest) {
        if (medicineDonationRequest.getMedicineExpirationDate()
                        .isBefore(ZonedDateTime.now())) {
            throw new FieldLimitException(ErrorCode.FORMAT_DONATION_EXPIRATION_DATE,
                            "medicine expiration date", medicineDonationRequest
                                            .getMedicineExpirationDate().toString());
        }

        if (medicineDonationRequest.getAmount() < 0
                        || medicineDonationRequest.getAmount() > 100000) {
            throw new FieldLimitException(ErrorCode.FORMAT_DONATION_AMOUNT, "amount",
                            String.valueOf(medicineDonationRequest.getAmount()));
        }
    }

    private void validateMandatoryCreate(
                    MedicineDonationRequest medicineDonationRequest) {
        if (medicineDonationRequest.getAmount() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_MEDICINE_DONATION_AMOUNT, "amount");
        }

        if (medicineDonationRequest.getMedicineId() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_MEDICINE_DONATION_MEDICINE,
                            "medicine_id");
        }
        if (medicineDonationRequest.getMedicineExpirationDate() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_MEDICINE_DONATION_MEDICINE_EXPIRATION_DATE,
                            "medicine_expiration_date");
        }
    }
}
