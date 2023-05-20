package org.example.entity;



import javax.validation.constraints.NotNull;
import lombok.Data;
import org.example.util.Role;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class Person {
    @Id
    private Long dni;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String phone;
    @Column(unique = true)
    @NotNull
    private String email;
    @NotNull
    private String address;
    @Lob @Basic(fetch = FetchType.LAZY)
    @NotNull
    private byte[] image;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
