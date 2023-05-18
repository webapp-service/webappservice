package org.example.dto;

import lombok.Data;
import org.example.entity.Attendance;
import org.example.entity.Provider;

import java.util.ArrayList;
import java.util.List;


@Data
public class ProviderDTO extends Provider {

    private Attendance attendance;
    private Integer score;

}
