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
    private Integer contractId;
    private Date contractDate;
    private String comment;
    private Integer score;
    @OneToOne
    private Status status;
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
