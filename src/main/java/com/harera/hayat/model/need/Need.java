package com.harera.hayat.model.need;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.harera.hayat.model.BaseEntity;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.donation.CommunicationMethod;
import com.harera.hayat.model.user.User;
import com.harera.hayat.repository.need.NeedStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "need")
public class Need extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "need_date")
    private OffsetDateTime needDate;

    @Column(name = "need_expiration_date")
    private OffsetDateTime needExpirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private NeedCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "communication_method")
    private CommunicationMethod communicationMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NeedStatus status;
}
