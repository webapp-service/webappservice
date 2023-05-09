package org.example.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Administradores")
@Data
public class Admin extends Persona {


}
