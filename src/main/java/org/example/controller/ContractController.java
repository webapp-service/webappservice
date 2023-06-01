package org.example.controller;

import org.example.dto.ProviderDTO;
import org.example.entity.Attendance;
import org.example.entity.Person;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    ContractServiceImpl contractService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    AttendanceServiceImpl attendanceService;
    @Autowired
    ProviderServiceImpl providerService;
    @Autowired
    ProviderDTOServiceImpl providerDTOService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public String create(@RequestParam Long providerId,
                         @RequestParam int attendanceId, HttpSession httpSession, ModelMap model) throws Exception {

        Person logged = (Person) httpSession.getAttribute("usersession");
        Long loggedDni = logged.getDni();

        try {
            contractService.createContract(attendanceId, providerId, loggedDni);
            return "redirect:/user/profile";

        } catch (Exception e) {

            model.put("error", e.getMessage());

            model.addAttribute("logged",logged.getName());
            model.addAttribute("rol",logged.getRole());
            List<ProviderDTO> listDTOs= providerDTOService.create();
            model.addAttribute("provider",listDTOs);
            List<Attendance> listAttendances= attendanceService.listAttendances();
            model.addAttribute("Attendances",listAttendances);

            return "index";
        }
    }
}