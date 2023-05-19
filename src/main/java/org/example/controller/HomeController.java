package org.example.controller;

import lombok.Getter;
import org.example.dto.ProviderDTO;
import org.example.entity.Attendance;
import org.example.service.AttendanceService;
import org.example.service.ProviderDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private ProviderDTOService providerDTOService;
    @Autowired
    private AttendanceService attendanceService;
    @GetMapping ("")
    public String listProvider(Model model){
        List<ProviderDTO> listDTOs= providerDTOService.create();
        model.addAttribute("provider",listDTOs);
        List<Attendance> listAttendances= attendanceService.listAttendances();
        model.addAttribute("Attendances",listAttendances);
        return "index.html";
    }
//    @GetMapping ("")
//    public String listAttendance(Model model){
//        List<Attendance> listAttendances= attendanceService.listAttendances();
//        model.addAttribute("Attendances",listAttendances);
//        return "index.html";
//    }


}