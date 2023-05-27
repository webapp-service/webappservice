package org.example.controller;


import org.example.entity.Contract;
import org.example.entity.Person;
import org.example.service.ContractServiceImpl;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    ContractServiceImpl contractService;
    @Autowired
    UserServiceImpl userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public String create(@RequestParam Long providerId,
                         @RequestParam int attendanceId, HttpSession httpSession) {
        Person logged = (Person) httpSession.getAttribute("usersession");
        Long loggedDni = logged.getDni();
        contractService.createContract(attendanceId, providerId, loggedDni);

        return "redirect:/user/profile";
    }
}