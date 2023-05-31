package org.example.controller;

import org.example.dto.ProviderDTO;
import org.example.entity.Attendance;
import org.example.entity.Contract;
import org.example.entity.Person;
import org.example.entity.Provider;
import org.example.repository.ContractRepository;
import org.example.service.AttendanceServiceImpl;
import org.example.service.ContractServiceImpl;
import org.example.service.ProviderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/vermas")
public class VermasController {

    @Autowired
    ContractServiceImpl contractService;
    @Autowired
    ProviderServiceImpl providerService;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    AttendanceServiceImpl attendanceService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{dni}/{attendanceId}")
    public String vermas(ModelMap model, @PathVariable Long dni, @PathVariable Integer attendanceId, HttpSession httpSession) {
        Person logged = (Person) httpSession.getAttribute("usersession");

        model.addAttribute("logged", logged.getName());
        model.addAttribute("rol", logged.getRole());
        List<Contract> contracts = contractRepository.findByUserAndAttendance(dni, attendanceId);
        model.addAttribute("contracts", contracts);
        Provider provider = providerService.getOne(dni);
        model.addAttribute("provider", provider);
        Attendance attendance = attendanceService.findAttendance(attendanceId).get();
        model.addAttribute("attendance", attendance);
        Integer score = providerService.averageScoreVermas(contracts);
        model.addAttribute("score", score);

        return "vermas";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/createContract")
    public String create(@RequestParam Long providerId,
                         @RequestParam int attendanceId, HttpSession httpSession, ModelMap model) {
        System.out.println("ENTRA??");
        Person logged = (Person) httpSession.getAttribute("usersession");
        Long userId = logged.getDni();
        model.addAttribute(logged);
        try {
            contractService.createContract(attendanceId, providerId, userId);
            return "user_menu";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/";
        }
    }


}
