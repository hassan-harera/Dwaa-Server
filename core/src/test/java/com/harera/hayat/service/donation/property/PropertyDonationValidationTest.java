package com.harera.hayat.service.donation.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.donation.CommunicationMethod;
import com.harera.hayat.model.donation.property.PropertyDonationRequest;
import com.harera.hayat.util.ErrorCode;

@ExtendWith(MockitoExtension.class)
class PropertyDonationValidationTest {

    private PropertyDonationValidation propertyDonationValidation;

    @BeforeEach
    public void setUp() {
        propertyDonationValidation = new PropertyDonationValidation();
    }

    /**
     * //mandatory validation: title, communicationMethod,
     * cityId, rooms, pathrooms, kitchens, availableFrom
     * availableTo, peopleCapacity
     */
    @Test
    void validateCreate_withoutTitle_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle(null);
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);

        // when
        MandatoryFieldException mandatoryFieldException = assertThrows(
                        MandatoryFieldException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_DONATION_TITLE,
                        mandatoryFieldException.getCode());
    }

    @Test
    void validateCreate_withoutCommunicationMethod_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCityId(1L);

        // when
        MandatoryFieldException mandatoryFieldException = assertThrows(
                        MandatoryFieldException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_DONATION_COMMUNICATION_METHOD,
                        mandatoryFieldException.getCode());
    }

    @Test
    void validateCreate_withoutCityId_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(null);

        // when
        MandatoryFieldException mandatoryFieldException = assertThrows(
                        MandatoryFieldException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_DONATION_CITY_ID,
                        mandatoryFieldException.getCode());
    }

    @Test
    void validateCreate_withoutRooms_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(null);

        // when
        MandatoryFieldException mandatoryFieldException = assertThrows(
                        MandatoryFieldException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_PROPERTY_DONATION_ROOMS,
                        mandatoryFieldException.getCode());
    }

    @Test
    void validateCreate_withoutBathrooms_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(null);

        // when
        MandatoryFieldException mandatoryFieldException = assertThrows(
                        MandatoryFieldException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_PROPERTY_DONATION_BATH_ROOMS,
                        mandatoryFieldException.getCode());
    }

    @Test
    void validateCreate_withoutKitchens_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(null);

        // when
        MandatoryFieldException mandatoryFieldException = assertThrows(
                        MandatoryFieldException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_PROPERTY_DONATION_KITCHENS,
                        mandatoryFieldException.getCode());
    }

    @Test
    void validateCreate_withoutPeopleCapacity_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(1);
        propertyDonationRequest.setPeopleCapacity(null);

        // when
        MandatoryFieldException mandatoryFieldException = assertThrows(
                        MandatoryFieldException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_PROPERTY_DONATION_PEOPLE_CAPACITY,
                        mandatoryFieldException.getCode());
    }

    @Test
    void validateCreate_withoutAvailableFrom_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(1);
        propertyDonationRequest.setPeopleCapacity(1);
        propertyDonationRequest.setAvailableFrom(null);

        // when
        MandatoryFieldException mandatoryFieldException = assertThrows(
                        MandatoryFieldException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_PROPERTY_DONATION_AVAILABLE_FROM,
                        mandatoryFieldException.getCode());
    }

    @Test
    void validateCreate_withoutAvailableTo_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(1);
        propertyDonationRequest.setPeopleCapacity(1);
        propertyDonationRequest.setAvailableFrom(OffsetDateTime.now());
        propertyDonationRequest.setAvailableTo(null);

        // when
        MandatoryFieldException mandatoryFieldException = assertThrows(
                        MandatoryFieldException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_PROPERTY_DONATION_AVAILABLE_TO,
                        mandatoryFieldException.getCode());
    }

    /**
     * roomsCount (1, 100), pathroomsCount (1, 100),
     * kitchensCount (1, 100),  availableFrom (after now),
     * availableTo (after availableFrom with at least 1 day)
     */

    @Test
    void validateCreate_withRoomsCountLessThanOne_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(0);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(1);
        propertyDonationRequest.setPeopleCapacity(1);
        propertyDonationRequest.setAvailableFrom(OffsetDateTime.now().plusHours(1));
        propertyDonationRequest.setAvailableTo(OffsetDateTime.now().plusDays(1));

        // when
        FieldFormatException invalidFormatException = assertThrows(
                        FieldFormatException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.FORMAT_PROPERTY_DONATION_ROOMS,
                        invalidFormatException.getCode());
    }

    @Test
    void validateCreate_withRoomsCountMoreThan100_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(101);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(1);
        propertyDonationRequest.setPeopleCapacity(1);
        propertyDonationRequest.setAvailableFrom(OffsetDateTime.now().plusHours(1));
        propertyDonationRequest.setAvailableTo(OffsetDateTime.now().plusDays(1));

        // when
        FieldFormatException invalidFormatException = assertThrows(
                        FieldFormatException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.FORMAT_PROPERTY_DONATION_ROOMS,
                        invalidFormatException.getCode());
    }

    @Test
    void validateCreate_withBathroomsCountLessThanOne_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(0);
        propertyDonationRequest.setKitchens(1);
        propertyDonationRequest.setPeopleCapacity(1);
        propertyDonationRequest.setAvailableFrom(OffsetDateTime.now().plusHours(1));
        propertyDonationRequest.setAvailableTo(OffsetDateTime.now().plusDays(1));

        // when
        FieldFormatException invalidFormatException = assertThrows(
                        FieldFormatException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.FORMAT_PROPERTY_DONATION_BATH_ROOMS,
                        invalidFormatException.getCode());
    }

    @Test
    void validateCreate_withKitchenCountLessThanOne_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(0);
        propertyDonationRequest.setPeopleCapacity(1);
        propertyDonationRequest.setAvailableFrom(OffsetDateTime.now().plusHours(1));
        propertyDonationRequest.setAvailableTo(OffsetDateTime.now().plusDays(1));

        // when
        FieldFormatException invalidFormatException = assertThrows(
                        FieldFormatException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.FORMAT_PROPERTY_DONATION_KITCHENS,
                        invalidFormatException.getCode());
    }

    @Test
    void validateCreate_withKitchenCountMoreThan100_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(101);
        propertyDonationRequest.setPeopleCapacity(1);
        propertyDonationRequest.setAvailableFrom(OffsetDateTime.now().plusHours(1));
        propertyDonationRequest.setAvailableTo(OffsetDateTime.now().plusDays(1));

        // when
        FieldFormatException invalidFormatException = assertThrows(
                        FieldFormatException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.FORMAT_PROPERTY_DONATION_KITCHENS,
                        invalidFormatException.getCode());
    }

    @Test
    void validateCreate_withBathroomsCountMoreThan100_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(101);
        propertyDonationRequest.setKitchens(1);
        propertyDonationRequest.setPeopleCapacity(1);
        propertyDonationRequest.setAvailableFrom(OffsetDateTime.now().plusHours(1));
        propertyDonationRequest.setAvailableTo(OffsetDateTime.now().plusDays(1));

        // when
        FieldFormatException invalidFormatException = assertThrows(
                        FieldFormatException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.FORMAT_PROPERTY_DONATION_BATH_ROOMS,
                        invalidFormatException.getCode());
    }

    @Test
    void validateCreate_withPeopleCapacityLessThanOne_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(1);
        propertyDonationRequest.setPeopleCapacity(0);
        propertyDonationRequest.setAvailableFrom(OffsetDateTime.now().plusHours(1));
        propertyDonationRequest.setAvailableTo(OffsetDateTime.now().plusDays(1));

        // when
        FieldFormatException invalidFormatException = assertThrows(
                        FieldFormatException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.FORMAT_PROPERTY_DONATION_PEOPLE_CAPACITY,
                        invalidFormatException.getCode());
    }

    @Test
    void validateCreate_withPeopleCapacityMoreThan1000_shouldThrowException() {
        // given
        PropertyDonationRequest propertyDonationRequest = new PropertyDonationRequest();
        propertyDonationRequest.setTitle("title");
        propertyDonationRequest.setCommunicationMethod(CommunicationMethod.CHAT);
        propertyDonationRequest.setCityId(1L);
        propertyDonationRequest.setRooms(1);
        propertyDonationRequest.setBathrooms(1);
        propertyDonationRequest.setKitchens(1);
        propertyDonationRequest.setPeopleCapacity(1001);
        propertyDonationRequest.setAvailableFrom(OffsetDateTime.now().plusHours(1));
        propertyDonationRequest.setAvailableTo(OffsetDateTime.now().plusDays(1));

        // when
        FieldFormatException invalidFormatException = assertThrows(
                        FieldFormatException.class, () -> propertyDonationValidation
                                        .validateCreate(propertyDonationRequest));
        // then
        assertEquals(ErrorCode.FORMAT_PROPERTY_DONATION_PEOPLE_CAPACITY,
                        invalidFormatException.getCode());
    }
}
