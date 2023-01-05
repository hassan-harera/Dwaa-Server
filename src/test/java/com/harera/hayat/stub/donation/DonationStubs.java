package com.harera.hayat.stub.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.city.City;
import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.DonationCategory;
import com.harera.hayat.model.donation.DonationState;
import com.harera.hayat.repository.donation.DonationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DonationStubs {

    private final DonationRepository donationRepository;

    public Donation create(String title, DonationCategory category, String description,
                    City city, DonationState state) {
        Donation donation = new Donation();
        donation.setId(0);
        donation.setTitle(title);
        donation.setCategory(category);
        donation.setCity(city);
        donation.setState(state);
        return donation;
    }

    public Donation insert(String title, String description, City city,
                    DonationCategory category, DonationState state) {
        Donation donation = create(title, category, description, city, state);
        donation.setId(0);
        return donationRepository.save(donation);
    }
}
