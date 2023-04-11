package com.reactly.backend.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "websites")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Website {

    @Id @Column
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Getter @Setter
    public String id;

    @ManyToOne
    @Getter @Setter
    public User user;

    @Column
    @Getter @Setter
    public String name;

    @Column
    @Getter @Setter
    public String content;
}
