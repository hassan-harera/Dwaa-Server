package com.harera.hayatserver.model.donation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import com.harera.hayatserver.model.BaseEntity;
import com.harera.hayatserver.model.category.Category;
import com.harera.hayatserver.model.city.City;
import com.harera.hayatserver.model.communication.CommunicationType;
import com.harera.hayatserver.model.user.User;

@Entity
@Table(name = "donation")
@Setter
@Getter
public class Donation extends BaseEntity {

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "description")
    private String donationDescription;

    @Basic
    @Column(name = "date")
    private ZonedDateTime donationDate;

    @Basic
    @Column(name = "expiration_date")
    private ZonedDateTime expirationDate;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "donation_type_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne(targetEntity = DonationState.class)
    @JoinColumn(name = "donation_state_id", referencedColumnName = "id")
    private DonationState donationState;

    @ManyToOne(targetEntity = CommunicationType.class)
    @JoinColumn(name = "communication_type_id", referencedColumnName = "id")
    private CommunicationType communicationType;

    @ManyToOne(targetEntity = City.class)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "uid", referencedColumnName = "id")
    private User user;
}
