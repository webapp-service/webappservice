package org.example.controller;

import org.example.service.ContractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public String create(@RequestParam Long userId, @RequestParam Long providerId,
                         @RequestParam int attendanceId){

        contractService.createContract(attendanceId, providerId, userId);

        return "user_menu.html";
    }

    @PostMapping("/opine/{id}")
    public String rateAndComment(@PathVariable int id, @RequestParam int score,
                                 @RequestParam String comment){

        contractService.qualify(id, score, comment);

        return "user_menu.html";
    }

}