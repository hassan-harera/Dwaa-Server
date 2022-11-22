package com.harera.hayatserver.service.city;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayatserver.exception.EntityNotFoundException;
import com.harera.hayatserver.model.city.City;
import com.harera.hayatserver.model.city.CityResponse;
import com.harera.hayatserver.repository.city.CityRepository;

import static com.harera.hayatserver.util.ErrorCode.NOT_FOUND_CITY_ID;


@Service
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CityService(CityRepository cityRepository,
                       ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public CityResponse get(long id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id),
                NOT_FOUND_CITY_ID));
        return modelMapper.map(city, CityResponse.class);
    }

    public List<CityResponse> list(long stateId) {
        List<City> cityList;
        if (stateId != 0) {
            cityList = cityRepository.findByStateId(stateId);
        } else {
            cityList = cityRepository.findAll();
        }
        return cityList.stream().map(city -> modelMapper.map(city,
                CityResponse.class)).collect(Collectors.toList());
    }

    public List<CityResponse> search(String arabicName, String englishName) {
        List<City> cities = cityRepository.search(arabicName, englishName);
        return cities.stream().map(city -> modelMapper.map(city,
                CityResponse.class)).collect(Collectors.toList());
    }
}
