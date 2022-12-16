package com.harera.hayat.model.city;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.harera.hayat.model.BaseEntity;

@Setter
@Getter
@Entity
@Table(name = "city")
public class City extends BaseEntity {

    @Basic
    @Column(name = "arabic_name")
    private String arabicName;
    @Basic
    @Column(name = "english_name")
    private String englishName;
    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;
}
