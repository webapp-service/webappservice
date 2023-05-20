package org.example.entity;


import lombok.Data;
import org.example.util.Role;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dni;
    private String name;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] image;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
