package org.example.controller;

import org.example.service.ContractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    ContractServiceImpl contractService;

    @GetMapping("/opine/id")
    public String opine(){

        return "opinar.html";
    }

    @PostMapping("/create")
    public String create(@RequestParam Long userId, @RequestParam Long providerId,
                         @RequestParam int attendanceId){

        contractService.createContract(attendanceId, providerId, userId);

        return "index.html";
    }

    // agrega score y comentario al contrato
    @PostMapping("/opine/id")
    public String rateAndComment(@RequestParam int contractId, @RequestParam int score,
                                 @RequestParam String comment){

        contractService.qualify(contractId, score, comment);

        return "index.html";
    }

}
