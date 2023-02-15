package com.harera.hayat.gateway.authorization.model.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.harera.hayat.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_")
public class User extends BaseEntity implements UserDetails {

    @Basic
    @Column(name = "phone_number")
    private String mobile;

    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @Column(name = "last_name")
    private String lastName;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "uid")
    private String uid;

    @Column(name = "device_token")
    private String deviceToken;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Collection<String> getRoles() {
        return getAuthorities().stream()
                        .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                        .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
