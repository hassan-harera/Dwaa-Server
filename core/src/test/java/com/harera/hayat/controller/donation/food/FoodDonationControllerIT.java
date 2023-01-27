package com.harera.hayat.core.controller.donation.food;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.harera.hayat.core.ApplicationIT;
import com.harera.hayat.core.model.city.City;
import com.harera.hayat.core.model.donation.CommunicationMethod;
import com.harera.hayat.core.model.donation.Donation;
import com.harera.hayat.core.model.donation.DonationCategory;
import com.harera.hayat.core.model.donation.DonationState;
import com.harera.hayat.core.model.donation.food.FoodDonation;
import com.harera.hayat.core.model.donation.food.FoodDonationRequest;
import com.harera.hayat.core.model.donation.food.FoodDonationResponse;
import com.harera.hayat.core.model.donation.food.FoodDonationUpdateRequest;
import com.harera.hayat.core.model.food.FoodUnit;
import com.harera.hayat.core.stub.city.CityStubs;
import com.harera.hayat.core.stub.donation.DonationStubs;
import com.harera.hayat.core.stub.donation.food.FoodDonationStubs;
import com.harera.hayat.core.stub.food.FoodUnitStubs;
import com.harera.hayat.core.util.DataUtil;
import com.harera.hayat.core.util.RequestUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class FoodDonationControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final DataUtil dataUtil;
    private final CityStubs cityStubs;
    private final FoodUnitStubs foodUnitStubs;
    private final FoodDonationStubs foodDonationStubs;
    private final DonationStubs donationStubs;

    @Test
    void create_withValidFoodDonationRequest_thenValidateMapping() {
        // Given
        String url = "/api/v1/donations/food";

        City city = cityStubs.insert("arabic_name", "english_name");
        FoodUnit foodUnit =
                        foodUnitStubs.insert("foodUnitArabicName", "foodUnitEnglishName");

        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(city.getId());
        request.setUnitId(foodUnit.getId());
        request.setAmount(2F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        ResponseEntity<FoodDonationResponse> responseEntity = null;
        FoodDonationResponse response = null;
        try {
            // When
            responseEntity = requestUtil.postWithAuth(url, request, null,
                            FoodDonationResponse.class);

            // Then
            assertEquals(200, responseEntity.getStatusCodeValue());

            response = responseEntity.getBody();
            assertNotNull(response);

            assertNotNull(response.getId());
            assertEquals(request.getTitle(), response.getTitle());
            assertEquals(request.getDescription(), response.getDescription());
            assertEquals(request.getAmount(), response.getAmount());
            assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            assertEquals(request.getCityId(), response.getCity().getId());
            assertEquals(request.getUnitId(), response.getUnit().getId());
            assertTrue(request.getFoodExpirationDate()
                            .isEqual(response.getFoodExpirationDate()));

            FoodDonation foodDonation = foodDonationStubs.get(response.getId());
            dataUtil.delete(foodDonation, foodDonation.getDonation());
        } finally {
            // Cleanup
            dataUtil.delete(city, foodUnit);
        }
    }

    @Test
    void update_withValidFoodDonationRequest_thenValidateMapping() {
        // Given
        String url = "/api/v1/donations/food/%d";

        City city = cityStubs.insert("arabic_name", "english_name");
        FoodUnit foodUnit =
                        foodUnitStubs.insert("foodUnitArabicName", "foodUnitEnglishName");

        Donation donation = donationStubs.insert("title", "description", city,
                        DonationCategory.FOOD, DonationState.PENDING);
        FoodDonation foodDonation = foodDonationStubs.insert(foodUnit, 1F,
                        OffsetDateTime.now(), donation);

        FoodDonationUpdateRequest request = new FoodDonationUpdateRequest();
        request.setCityId(city.getId());
        request.setUnitId(foodUnit.getId());
        request.setAmount(2F);
        request.setTitle("new_title");
        request.setDescription("new_desc");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        try {
            // When
            ResponseEntity<FoodDonationResponse> responseEntity =
                            requestUtil.putWithAuth(url.formatted(foodDonation.getId()),
                                            request, null, FoodDonationResponse.class);

            // Then
            assertEquals(200, responseEntity.getStatusCodeValue());

            FoodDonationResponse response = responseEntity.getBody();
            assertNotNull(response);

            assertEquals(request.getTitle(), response.getTitle());
            assertEquals(request.getDescription(), response.getDescription());
            assertEquals(request.getAmount(), response.getAmount());
            assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            assertEquals(request.getCityId(), response.getCity().getId());
            assertEquals(request.getUnitId(), response.getUnit().getId());
            assertTrue(request.getFoodExpirationDate()
                            .isEqual(response.getFoodExpirationDate()));
        } finally {
            // Cleanup
            dataUtil.delete(city, foodUnit, foodDonation, donation);
        }
    }

    @Test
    void get_thenValidateMapping() {
        // Given
        String url = "/api/v1/donations/food/%d";

        City city = cityStubs.insert("arabic_name", "english_name");
        FoodUnit foodUnit =
                        foodUnitStubs.insert("foodUnitArabicName", "foodUnitEnglishName");

        Donation donation = donationStubs.insert("title", "description", city,
                        DonationCategory.FOOD, DonationState.PENDING);
        FoodDonation foodDonation = foodDonationStubs.insert(foodUnit, 1F,
                        OffsetDateTime.now(), donation);

        try {
            // When
            ResponseEntity<FoodDonationResponse> responseEntity =
                            requestUtil.getWithAuth(url.formatted(foodDonation.getId()),
                                            null, null, FoodDonationResponse.class);

            // Then
            assertEquals(200, responseEntity.getStatusCodeValue());

            FoodDonationResponse response = responseEntity.getBody();
            assertNotNull(response);

            assertEquals(donation.getTitle(), response.getTitle());
            assertEquals(donation.getDescription(), response.getDescription());
            assertEquals(donation.getCommunicationMethod(),
                            response.getCommunicationMethod());
            assertEquals(donation.getCity().getId(), response.getCity().getId());
            assertEquals(foodDonation.getAmount(), response.getAmount());
            assertEquals(foodDonation.getUnit().getId(), response.getUnit().getId());
            assertTrue(foodDonation.getFoodExpirationDate().toLocalDate()
                            .isEqual(response.getFoodExpirationDate().toLocalDate()));
        } finally {
            // Cleanup
            dataUtil.delete(city, foodUnit, foodDonation, donation);
        }
    }

}
