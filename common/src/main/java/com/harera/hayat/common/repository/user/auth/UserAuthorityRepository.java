package com.harera.hayat.common.repository.user.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.common.model.user.auth.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
