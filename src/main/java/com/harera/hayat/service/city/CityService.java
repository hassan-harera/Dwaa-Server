package com.harera.hayat.service.city;

import static com.harera.hayat.util.ErrorCode.NOT_FOUND_CITY_ID;
import static com.harera.hayat.util.ObjectMapperUtils.mapAll;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.city.CityResponse;
import com.harera.hayat.repository.city.CityRepository;


@Service
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final Environment env;

    public CityService(CityRepository cityRepository,
                    ModelMapper modelMapper, Environment env) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.env = env;
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

    public List<CityResponse> search(String query, Integer page) {
        page = Integer.max(page, 1) - 1;
        int pageSize = Integer.parseInt(env.getProperty("cities.search_page_size", "10"));
        List<City> cities = cityRepository.search(query,
                        Pageable.ofSize(pageSize).withPage(page));
        return mapAll(cities, CityResponse.class);
    }

    public City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(
                        () -> new EntityNotFoundException(City.class, cityId));
    }
}
