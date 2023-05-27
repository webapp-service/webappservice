package org.example.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.entity.Attendance;
import org.example.entity.Provider;
import org.example.entity.Status;
import org.example.entity.User;

@Data
public class ContractDTO {
    @Getter
    @Setter
    private Integer contractDtoId;
    private Integer contractId;
    private Provider provider;
    private User user;
    private Attendance attendance;
    private Status status;

}
