package org.example.Service;

import org.example.entity.Attendance;
import org.example.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    public Attendance create(String name) throws Exception {
        Attendance attendance = new Attendance();

        if (name != null && !name.isEmpty()) {
            attendance.setName(name);
            attendanceRepository.save(attendance);

        } else throw new Exception("name cannot be empty or null");
        return attendance;
    }
}
