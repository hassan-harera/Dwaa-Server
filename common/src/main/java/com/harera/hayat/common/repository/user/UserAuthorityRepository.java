package com.harera.hayat.common.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.common.model.user.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
