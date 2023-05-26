package org.example.service;

import org.example.dto.ProviderDTO;
import org.example.entity.Attendance;

import java.util.List;
import java.util.Optional;

public interface ProviderDTOService {
    List<ProviderDTO> create();

    Optional<Attendance> getAttendances(Integer idAttendance);

    Integer averageScore(long idProvider, Integer idAttendance);

}
