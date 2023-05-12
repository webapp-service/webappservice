package org.example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "status")
@Data
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;

    public Status(String name) {
        this.name = name;
    }


    public Status() {

    }
}
