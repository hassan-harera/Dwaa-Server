package com.harera.dwaaserver.repository;

import com.harera.dwaaserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query("select u from UserEntity u where u.username = ?1")
    Optional<UserEntity> getUserWithUsername(@NonNull String username);

    @Query("select u from UserEntity u where u.uid = ?1")
    Optional<UserEntity> getUserWithUid(@NonNull Integer uid);

    @Query("select u from UserEntity u where u.email = ?1")
    Optional<UserEntity> getUserWithEmail(@NonNull String email);

    @Query("select u from UserEntity u where u.phoneNumber = ?1")
    Optional<UserEntity> getUserWithPhoneNumber(@NonNull String phoneNumber);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.firstName = ?1 where u.uid = ?2")
    int updateFirstNameWithUid(@NonNull String name, @NonNull String uid);

    @Transactional
    @Modifying
    @Query("update UserEntity  u set u.lastName = ?1 where u.uid = ?2")
    int updateLastNameWithUid(@NonNull String name, @NonNull String uid);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.password = ?1 where u.uid = ?2")
    int updatePasswordWithUid(@NonNull String password, String uid);
}
