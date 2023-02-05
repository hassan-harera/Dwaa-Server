package com.harera.hayat.common.model.user.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.harera.hayat.common.model.user.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_authorities")
@IdClass(UserAuthorityId.class)
public class UserAuthority implements GrantedAuthority {

    @Id
    @Column(name = "authority")
    private String authority;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public String getAuthority() {
        return authority;
    }
}
