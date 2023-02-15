package com.harera.hayat.donation.model.donation.image;


import javax.persistence.JoinColumn;

import com.harera.hayat.common.model.BaseDocumentDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DonationImageDto extends BaseDocumentDto {

    @JoinColumn(name = "url")
    private String imageUrl;
}
