package com.harera.hayat.needs.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.harera.hayat.framework.model.BaseEntity;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.user.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class Need extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "need_date")
    private LocalDateTime needDate;

    @Column(name = "need_expiration_date")
    private LocalDateTime needExpirationDate;

    @Column(name = "category")
    private NeedCategory category;

    @Column(name = "status")
    private NeedState status;

    @Column(name = "communication_method")
    private CommunicationMethod communicationMethod;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
