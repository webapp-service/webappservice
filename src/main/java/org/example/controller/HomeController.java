package org.example.controller;

import org.example.dto.ProviderDTO;
import org.example.entity.Attendance;
import org.example.entity.Person;
import org.example.service.AttendanceServiceImpl;
import org.example.service.ProviderDTOServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private ProviderDTOServiceImpl providerDTOServiceImpl;
    @Autowired
    private AttendanceServiceImpl attendanceServiceImpl;
    @GetMapping ("")
    public String listProvider(ModelMap model, HttpSession httpSession){
        Person logged = (Person) httpSession.getAttribute("usersession");
        if (logged!= null){
            model.addAttribute("logged",logged.getName());
        }
        List<ProviderDTO> listDTOs= providerDTOServiceImpl.create();
        model.addAttribute("provider",listDTOs);
        List<Attendance> listAttendances= attendanceServiceImpl.listAttendances();
        model.addAttribute("Attendances",listAttendances);
        return "index.html";
    }

    @GetMapping ("/attendance/{id}")
    public String secondAttendance(Model model, @PathVariable Integer id){
        List<ProviderDTO> listDTOs= providerDTOServiceImpl.filterAttendance(id);
        model.addAttribute("provider",listDTOs);
        List<Attendance> listAttendances= attendanceServiceImpl.listAttendances();
        model.addAttribute("Attendances",listAttendances);
        return "index.html";
    }



}