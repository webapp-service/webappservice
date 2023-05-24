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

    @GetMapping("/opine/{id}")
    public String opine(@PathVariable Integer id, ModelMap model){

        model.put("contract", contractService.getContractById(id));

        return "opinar.html";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public String create(@RequestParam Long providerId,
                         @RequestParam int attendanceId , HttpSession httpSession, ModelMap model){
        Person logged = (Person) httpSession.getAttribute("usersession");
        Long userId = logged.getDni();

        try{
            contractService.createContract(attendanceId, providerId, userId);
            return "user_menu.html";
        } catch(Exception e){
            model.put("error", e.getMessage());
            return "index.html";
        }
    }

    @PostMapping("/opine/{id}")
    public String rateAndComment(@PathVariable int id, @RequestParam int score,
                                 @RequestParam String comment){

//        contractService.qualify(id, score, comment);

        return "user_menu.html";
    }

}