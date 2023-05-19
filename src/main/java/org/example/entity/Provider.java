package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "providers")
@Data
public class Provider extends Person {

    private String description;
    private Double pricePerHour;


    @ManyToMany
    @JoinTable(name = "providers_attendances",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "attendance_id"))
    private List<Attendance> attendances;



    @OneToMany(mappedBy = "provider")
    private List<Contract> contracts;


}

