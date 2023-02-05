package com.harera.hayat.controller.donation.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.harera.hayat.ApplicationIT;
import com.harera.hayat.common.model.city.City;
import com.harera.hayat.model.donation.CommunicationMethod;
import com.harera.hayat.model.donation.DonationCategory;
import com.harera.hayat.model.donation.DonationState;
import com.harera.hayat.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.model.donation.property.PropertyDonationResponse;
import com.harera.hayat.stub.city.CityStubs;
import com.harera.hayat.stub.donation.property.PropertyDonationStubs;
import com.harera.hayat.util.DataUtil;
import com.harera.hayat.util.RequestUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PropertyDonationControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final DataUtil dataUtil;
    private final PropertyDonationStubs propertyDonationStubs;
    private final CityStubs cityStubs;

    @Test
    void create_with_then() {
        // Given
        City city = cityStubs.insert("arabic_name", "english_name");

        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(1);
        propertyDonationRequest.setPeopleCapacity(1);
        propertyDonationRequest.setCityId(city.getId());

        String url = "/api/v1/donations/property";
        try {
            // When
            ResponseEntity<PropertyDonationResponse> responseEntity =
                            requestUtil.postWithAuth(url, propertyDonationRequest, null,
                                            PropertyDonationResponse.class);
            PropertyDonationResponse propertyDonationResponse = responseEntity.getBody();

            // Then
            assertEquals(200, responseEntity.getStatusCodeValue());
            assertNotNull(propertyDonationResponse);

            Assertions.assertEquals(propertyDonationRequest.getTitle(),
                            propertyDonationResponse.getTitle());
            Assertions.assertEquals(propertyDonationRequest.getCommunicationMethod(),
                            propertyDonationResponse.getCommunicationMethod());
            Assertions.assertEquals(propertyDonationRequest.getCityId(),
                            propertyDonationResponse.getCity().getId());
            Assertions.assertEquals(propertyDonationRequest.getRooms(),
                            propertyDonationResponse.getRooms());
            Assertions.assertEquals(propertyDonationRequest.getBathrooms(),
                            propertyDonationResponse.getBathrooms());
            Assertions.assertEquals(propertyDonationRequest.getKitchens(),
                            propertyDonationResponse.getKitchens());
            Assertions.assertEquals(DonationCategory.PROPERTY,
                            propertyDonationResponse.getCategory());
            Assertions.assertEquals(DonationState.PENDING,
                            propertyDonationResponse.getState());

            dataUtil.delete(propertyDonationStubs.get(propertyDonationResponse.getId()));
        } finally {
            // Cleanup
            dataUtil.delete(city);
        }
    }

}
