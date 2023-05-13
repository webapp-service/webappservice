package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "providers")
@Data
public class Provider extends Person {

    String description;
    Double pricePerHour;


    @ManyToMany
    @JoinTable(name = "providers_attendances",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "attendance_id"))
    List<Attendance> attendances;



    @OneToMany(mappedBy = "provider")
    List<Contract> contracts;


}

