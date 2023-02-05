package com.harera.hayat.common.service.city;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayat.common.exception.EntityNotFoundException;
import com.harera.hayat.common.model.city.State;
import com.harera.hayat.common.model.city.StateResponse;
import com.harera.hayat.common.repository.city.StateRepository;
import com.harera.hayat.common.util.ErrorCode;


@Service
public class StateService {

    private final StateRepository stateRepository;
    private final ModelMapper mapper;

    @Autowired
    public StateService(StateRepository stateRepository, ModelMapper mapper) {
        this.stateRepository = stateRepository;
        this.mapper = mapper;
    }

    public StateResponse get(long id) {
        State state = stateRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id),
                                        ErrorCode.NOT_FOUND_STATE_ID));
        return mapper.map(state, StateResponse.class);
    }

    public List<StateResponse> list() {
        List<State> stateList = stateRepository.findAll();
        return stateList.stream().map(state -> mapper.map(state,
                StateResponse.class)).collect(Collectors.toList());
    }
}
