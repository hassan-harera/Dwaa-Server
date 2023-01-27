package com.harera.hayat.core.repository.city;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.core.model.city.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
