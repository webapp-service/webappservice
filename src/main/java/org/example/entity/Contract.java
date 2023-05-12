package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Contracts")
@Data
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Date contractDate;
    String comment;

    Double Score;
    @OneToOne
    Status status;


    @ManyToOne
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
