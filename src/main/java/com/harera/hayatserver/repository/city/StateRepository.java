package com.harera.hayatserver.repository.city;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayatserver.model.city.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
