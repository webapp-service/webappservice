package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attendances")
@Data
public class Attendance {
    @Id
    private Integer id;
    private String name;

    @ManyToMany
    @JoinTable(name = "providers_attendances",
            joinColumns = @JoinColumn(name = "attendance_id"),
            inverseJoinColumns = @JoinColumn(name = "provider_id"))
    List<Provider> providers = new ArrayList<>();
}
