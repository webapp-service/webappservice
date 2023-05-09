package org.example.entity;

import lombok.Data;

import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario extends Persona {


}
