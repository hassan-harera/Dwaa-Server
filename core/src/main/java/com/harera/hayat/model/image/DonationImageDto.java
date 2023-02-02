package com.harera.hayat.model.image;

import javax.persistence.JoinColumn;

import com.harera.hayat.model.BaseEntityDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DonationImageDto extends BaseEntityDto {

    @JoinColumn(name = "url")
    private String imageUrl;
}
