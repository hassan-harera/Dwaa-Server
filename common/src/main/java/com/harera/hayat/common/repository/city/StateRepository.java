package com.harera.hayat.common.repository.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.common.model.city.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
