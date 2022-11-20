package com.harera.hayatserver.model.city;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.harera.hayatserver.model.BaseEntity;

@Entity
@Table(name = "city", schema = "public", catalog = "Dwaa")
@Setter
@Getter
public class City extends BaseEntity {

    @Basic
    @Column(name = "city_name_ar")
    private String cityNameAr;

    @Basic
    @Column(name = "city_name_en")
    private String cityNameEn;

    @Basic
    @Column(name = "state_id")
    private int stateId;
}
