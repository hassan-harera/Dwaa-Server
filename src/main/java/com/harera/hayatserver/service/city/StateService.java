package com.harera.hayatserver.service.city;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayatserver.exception.EntityNotFoundException;
import com.harera.hayatserver.model.city.State;
import com.harera.hayatserver.model.city.StateResponse;
import com.harera.hayatserver.repository.city.StateRepository;

import static com.harera.hayatserver.util.ErrorCode.NOT_FOUND_STATE_ID;


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
                                        NOT_FOUND_STATE_ID));
        return mapper.map(state, StateResponse.class);
    }

    public List<StateResponse> list() {
        List<State> stateList = stateRepository.findAll();
        return stateList.stream().map(state -> mapper.map(state,
                StateResponse.class)).collect(Collectors.toList());
    }
}
