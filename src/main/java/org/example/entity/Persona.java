package org.example.entity;


import lombok.Data;
import org.example.util.Rol;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public abstract class Persona {
    @Id
    Long dni;
    String nombre;
    String apellido;
    Long telefono;
    String mail;
    String direccion;
    String imagen;
    String password;
    @Enumerated(EnumType.STRING)
    Rol rol;
}
