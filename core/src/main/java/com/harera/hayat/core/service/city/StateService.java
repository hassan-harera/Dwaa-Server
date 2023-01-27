package com.harera.hayat.core.service.city;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayat.core.exception.EntityNotFoundException;
import com.harera.hayat.core.model.city.State;
import com.harera.hayat.core.model.city.StateResponse;
import com.harera.hayat.core.repository.city.StateRepository;
import com.harera.hayat.core.util.ErrorCode;


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
