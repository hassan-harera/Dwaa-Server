package com.harera.hayat.service.donation;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.DonationDto;
import com.harera.hayat.util.ErrorCode;
import com.harera.hayat.util.FieldFormat;
import com.harera.hayat.validation.PagingValidation;

@Service
public class DonationValidation {

    public void validateList(Integer page, Integer size) {
        PagingValidation.validate(page, size);
    }

    public void validateDonation(DonationDto donationDto) {
        validateMandatory(donationDto);
        validateFormat(donationDto);
    }

    private void validateFormat(DonationDto donationDto) {
        if (donationDto.getTitle().length() < 4
                        || donationDto.getTitle().length() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_DONATION_TITLE, "title",
                            FieldFormat.TITLE_PATTERN);
        }
    }

    private void validateMandatory(DonationDto donationDto) {
        if (isBlank(donationDto.getTitle())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_DONATION_TITLE,
                            "title");
        }
        if (donationDto.getCommunicationMethod() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_DONATION_COMMUNICATION_METHOD,
                            "communication_method");
        }
        if (donationDto.getCityId() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_DONATION_CITY_ID,
                            "city_id");
        }
    }
}
