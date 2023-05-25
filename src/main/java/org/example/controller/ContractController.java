package org.example.controller;

import org.example.entity.Person;
import org.example.service.ContractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    ContractServiceImpl contractService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public String create(@RequestParam Long providerId,
                         @RequestParam int attendanceId, HttpSession httpSession, ModelMap model) {
        Person logged = (Person) httpSession.getAttribute("usersession");
        Long userId = logged.getDni();

        try {
            contractService.createContract(attendanceId, providerId, userId);
            return "user_menu";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/rate/{contractId}")
    public String opine(@PathVariable Integer contractId, ModelMap model) {
        model.put("contractId", contractId);

        return "rate_provider";
    }

    @PostMapping("/rate/{contractId}")
    public String rateAndComment(@PathVariable int contractId, @RequestParam int rate, @RequestParam String comment,
                                 ModelMap model) {
        try {
            contractService.qualify(contractId, rate, comment);
        } catch (Exception e) {
            model.put("contractId", contractId);
            model.put("rate", rate);
            model.put("comment", comment);
            model.put("error", e.getMessage());

            return "rate_provider";
        }
        return "redirect:/user/profile";
    }

}