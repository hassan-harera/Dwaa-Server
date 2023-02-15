package com.harera.hayat.authorization.repository.user.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.authorization.model.user.auth.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
