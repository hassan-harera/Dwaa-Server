package com.harera.hayat.repository.user.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.model.user.auth.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
