package com.harera.hayatserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.harera.hayatserver.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.username = ?1")
    Optional<User> getUserWithUsername(@NonNull String username);

    @Query("select u from User u where u.id = ?1")
    Optional<User> getUserWithUid(@NonNull Integer uid);

    @Query("select u from User u where u.email = ?1")
    Optional<User> getUserWithEmail(@NonNull String email);

    @Query("select u from User u where u.phoneNumber = ?1")
    Optional<User> getUserWithPhoneNumber(@NonNull String phoneNumber);

    @Transactional
    @Modifying
    @Query("update User u set u.firstName = ?1 where u.id = ?2")
    int updateFirstNameWithUid(@NonNull String name, @NonNull String uid);

    @Transactional
    @Modifying
    @Query("update User  u set u.lastName = ?1 where u.id = ?2")
    int updateLastNameWithUid(@NonNull String name, @NonNull String uid);

    @Transactional
    @Modifying
    @Query("update User u set u.password = ?1 where u.id = ?2")
    int updatePasswordWithUid(@NonNull String password, String uid);

    boolean existsByPhoneNumber(String subject);

    boolean existsByEmail(String subject);

    boolean existsByUsername(String subject);

    User findByPhoneNumber(String subject);

    User findByEmail(String subject);

    User findByUsername(String subject);
}
