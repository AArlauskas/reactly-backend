package com.reactly.backend.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.reactly.backend.enums.UserRoles;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {

    @Id @Column
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Getter @Setter
    public String id;

    @Column
    @Getter @Setter
    public String firstName;

    @Column
    @Getter @Setter
    public String lastName;

    @Column
    @Getter @Setter
    public String email;

    @Column
    @Getter @Setter
    public String password;

    @Column
    @Getter @Setter
    private String role = UserRoles.BASIC.toString();

    @Getter @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Website> websites;

    public User() {
    }
}
