package org.example.entity;


import lombok.Data;
import org.example.util.Rol;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long dni;
    String name;
    String lastName;
    Long phone;
    String email;
    String address;
    String image;
    String password;
    @Enumerated(EnumType.STRING)
    Rol rol;
}
