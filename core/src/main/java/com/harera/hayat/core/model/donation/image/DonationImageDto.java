package com.harera.hayat.core.model.donation.image;

import javax.persistence.JoinColumn;

import com.harera.hayat.core.model.BaseEntityDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DonationImageDto extends BaseEntityDto {

    @JoinColumn(name = "url")
    private String imageUrl;
}
