package com.harera.hayat.model.user.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.harera.hayat.model.BaseEntity;
import com.harera.hayat.model.user.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_authorities")
public class UserAuthority extends BaseEntity implements GrantedAuthority {

    @Column(name = "authority")
    private String authority;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public String getAuthority() {
        return authority;
    }
}
