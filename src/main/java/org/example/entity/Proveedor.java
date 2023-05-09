package org.example.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "proveedores")
@Data
public class Proveedor extends Persona {

}

