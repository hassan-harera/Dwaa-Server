package com.harera.hayat.common.model.city;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.harera.hayat.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "state")
public class State extends BaseEntity {

    @Basic
    @Column(name = "arabic_name")
    private String arabicName;

    @Basic
    @Column(name = "english_name")
    private String englishName;

    @OneToMany(mappedBy = "state")
    private List<City> cities;
}
