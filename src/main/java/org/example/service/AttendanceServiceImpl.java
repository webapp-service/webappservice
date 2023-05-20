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
public class AttendanceServiceImpl implements AttendanceService{

    @Autowired
    AttendanceRepository attendanceRepository;

    @Transactional
    @Override
    public Attendance create(String name) throws Exception {
        Attendance attendance = new Attendance();

        if (name != null && !name.isEmpty()) {
            attendance.setName(name);
            attendanceRepository.save(attendance);

        } else throw new Exception("el nombre no puede estar vacio");
        return attendance;
    }
    @Transactional(readOnly = true)
    @Override
    public List<Attendance> listAttendances(){
        List<Attendance> attendances = new ArrayList<>();
        attendances = attendanceRepository.findAll();

        return attendances;
    }
    @Transactional
    @Override
    public void modifyAttendance(Integer id, String name){
        Optional<Attendance> answer = attendanceRepository.findById(id);
        if (answer.isPresent()){
            Attendance attendance = answer.get();
            attendance.setName(name);
            attendanceRepository.save(attendance);
        }
    }
    @Override
    public Optional<Attendance> findAttendance(Integer id){
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        return attendance;
    }
    @Transactional
    @Override
    public void deleteAttendance(Attendance attendance){
        attendanceRepository.delete(attendance);
    }

}
