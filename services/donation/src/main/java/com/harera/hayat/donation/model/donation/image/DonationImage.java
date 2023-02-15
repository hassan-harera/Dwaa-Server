package com.harera.hayat.donation.model.donation.image;

import javax.persistence.Basic;
import javax.persistence.Column;

import org.springframework.data.mongodb.core.mapping.Document;

import com.harera.hayat.common.model.BaseDocument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "donation_images")
public class DonationImage extends BaseDocument {

    @Basic
    @Column(name = "url")
    private String imageUrl;
}
