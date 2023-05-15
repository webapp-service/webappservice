package org.example.service;

import org.example.entity.Attendance;
import org.example.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Transactional
    public Attendance create(String name) throws Exception {
        Attendance attendance = new Attendance();

        if (name != null && !name.isEmpty()) {
            attendance.setName(name);
            attendanceRepository.save(attendance);

        } else throw new Exception("name cannot be empty or null");
        return attendance;
    }
    @Transactional(readOnly = true)
    public List<Attendance> listAttendances(){
        List<Attendance> attendances = new ArrayList<>();
        attendances = attendanceRepository.findAll();

        return attendances;
    }
    @Transactional
    public void modifyAttendance(Integer id, String name){
        Optional<Attendance> answer = attendanceRepository.findById(id);
        if (answer.isPresent()){
            Attendance attendance = answer.get();
            attendance.setName(name);
            attendanceRepository.save(attendance);
        }
    }
    public void findAttendance(Attendance attendance){
        attendanceRepository.findById(attendance.getId());
    }
    @Transactional
    public void deleteAttendance(Attendance attendance){
        attendanceRepository.delete(attendance);
    }

}
