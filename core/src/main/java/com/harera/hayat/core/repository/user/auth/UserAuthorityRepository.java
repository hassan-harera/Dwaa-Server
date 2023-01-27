package com.harera.hayat.core.repository.user.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.core.model.user.auth.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
