package org.example.service;

import org.example.entity.Attendance;

import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    Attendance create(String name) throws Exception;

    List<Attendance> listAttendances();

    void modifyAttendance(Integer id, String name);

    Optional<Attendance> findAttendance(Integer id);

    void deleteAttendance(Attendance attendance);
}
