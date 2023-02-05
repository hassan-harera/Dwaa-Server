package com.harera.hayat.donation.service.donation.medicine;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.harera.hayat.common.exception.FieldLimitException;
import com.harera.hayat.common.exception.MandatoryFieldException;
import com.harera.hayat.common.util.ErrorCode;
import com.harera.hayat.donation.model.donation.medicine.MedicineDonationRequest;
import com.harera.hayat.donation.service.donation.DonationValidation;

@Service
public class MedicineDonationValidation extends DonationValidation {

    public void validateCreate(MedicineDonationRequest medicineDonationRequest) {
        validateDonation(medicineDonationRequest);
        validateMandatoryCreate(medicineDonationRequest);
        validateFormatCreate(medicineDonationRequest);
    }

    private void validateFormatCreate(MedicineDonationRequest medicineDonationRequest) {
        if (medicineDonationRequest.getMedicineExpirationDate()
                        .isBefore(OffsetDateTime.now())) {
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
