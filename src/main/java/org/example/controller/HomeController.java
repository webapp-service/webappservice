package org.example.controller;

import org.example.dto.ProviderDTO;
import org.example.entity.Attendance;
import org.example.service.AttendanceServiceImpl;
import org.example.service.ProviderDTOServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private ProviderDTOServiceImpl providerDTOServiceImpl;
    @Autowired
    private AttendanceServiceImpl attendanceServiceImpl;
    @GetMapping ("")
    public String listProvider(Model model){
        List<ProviderDTO> listDTOs= providerDTOServiceImpl.create();
        model.addAttribute("provider",listDTOs);
        List<Attendance> listAttendances= attendanceServiceImpl.listAttendances();
        model.addAttribute("Attendances",listAttendances);
        return "index.html";
    }

    @GetMapping ("/attendance/1")
    public String fistAttendance(Model model){
        List<ProviderDTO> listDTOs= providerDTOServiceImpl.filterFistAttendance();
        model.addAttribute("provider",listDTOs);
        List<Attendance> listAttendances= attendanceServiceImpl.listAttendances();
        model.addAttribute("Attendances",listAttendances);
        return "index.html";
    }

    @GetMapping ("/attendance/2")
    public String secondAttendance(Model model){
        List<ProviderDTO> listDTOs= providerDTOServiceImpl.filterSecondAttendance();
        model.addAttribute("provider",listDTOs);
        List<Attendance> listAttendances= attendanceServiceImpl.listAttendances();
        model.addAttribute("Attendances",listAttendances);
        return "index.html";
    }

    @GetMapping ("/attendance/3")
    public String thirdAttendance(Model model){
        List<ProviderDTO> listDTOs= providerDTOServiceImpl.filterThirdAttendance();
        model.addAttribute("provider",listDTOs);
        List<Attendance> listAttendances= attendanceServiceImpl.listAttendances();
        model.addAttribute("Attendances",listAttendances);
        return "index.html";
    }
    @GetMapping ("/attendance/4")
    public String fourthAttendance(Model model){
        List<ProviderDTO> listDTOs= providerDTOServiceImpl.filterFourthAttendance();
        model.addAttribute("provider",listDTOs);
        List<Attendance> listAttendances= attendanceServiceImpl.listAttendances();
        model.addAttribute("Attendances",listAttendances);
        return "index.html";
    }



}