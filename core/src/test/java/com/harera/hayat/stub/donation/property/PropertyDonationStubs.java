package com.harera.hayat.stub.donation.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayat.common.model.city.City;
import com.harera.hayat.model.donation.DonationCategory;
import com.harera.hayat.model.donation.DonationState;
import com.harera.hayat.model.donation.property.PropertyDonation;
import com.harera.hayat.repository.donation.property.PropertyDonationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyDonationStubs {

    private final PropertyDonationRepository propertyDonationRepository;

    public PropertyDonation create(String title, DonationCategory category,
                    String description, City city, DonationState state, Integer rooms,
                    Integer bathrooms, Integer kitchens) {
        PropertyDonation propertyDonation = new PropertyDonation();
        propertyDonation.setId(0L);
        propertyDonation.setTitle(title);
        propertyDonation.setCategory(category);
        propertyDonation.setCity(city);
        propertyDonation.setState(state);
        return propertyDonation;
    }

    public PropertyDonation insert(String title, DonationCategory category,
                    String description, City city, DonationState state, Integer rooms,
                    Integer bathrooms, Integer kitchens) {
        PropertyDonation propertyDonation = create(title, category, description, city,
                        state, rooms, bathrooms, kitchens);
        return propertyDonationRepository.save(propertyDonation);
    }

    public PropertyDonation get(Long id) {
        return propertyDonationRepository.findById(id).orElse(null);
    }
}
