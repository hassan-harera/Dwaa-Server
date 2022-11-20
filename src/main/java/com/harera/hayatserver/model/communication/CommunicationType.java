package com.harera.hayatserver.model.communication;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.harera.hayatserver.model.BaseEntity;

@Entity
@Table(name = "communication_type", schema = "public", catalog = "Dwaa")
@Setter
@Getter
public class CommunicationType extends BaseEntity {

    @Basic
    @Column(name = "donation_type")
    private String donationType;
}
