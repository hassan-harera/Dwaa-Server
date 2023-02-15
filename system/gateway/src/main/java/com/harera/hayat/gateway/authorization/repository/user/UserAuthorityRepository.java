package com.harera.hayat.gateway.authorization.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.gateway.authorization.model.user.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
