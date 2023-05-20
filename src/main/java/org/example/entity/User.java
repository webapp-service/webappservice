package org.example.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User extends Person {

    @OneToMany(mappedBy = "user")
    private List<Contract> contracts = new ArrayList<>();


}
