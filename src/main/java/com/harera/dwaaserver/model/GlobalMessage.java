package com.harera.dwaaserver.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "global_message")
@Entity
@Data
public class GlobalMessage extends BaseEntity {
    @Column(name = "code_")
    private String code;
    @Column(name = "language_")
    private String language;
    @Column(name = "message_")
    private String message;
}
