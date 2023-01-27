package com.harera.hayat.core.model.donation.medicine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "active", })
public class MedicineDonationResponse extends
                com.harera.hayat.core.model.donation.medicine.MedicineDonationDto {

}
