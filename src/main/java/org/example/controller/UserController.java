package org.example.controller;

import org.example.service.ContractServiceImpl;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    ContractServiceImpl contractService;

    @GetMapping("contract/{id}")
    public String userMenu(@PathVariable Long id, ModelMap model){

        model.addAttribute("user", userService.GetOneById(id));
        model.addAttribute("userContracts", contractService.getAllContractsByUser(id));

        return "user_menu.html";
    }

    @GetMapping("/register")
    public String register(){

        return "user_form.html";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email,
                           @RequestParam String password1, @RequestParam Long dni,
                           @RequestParam String lastName, @RequestParam String address,
                           @RequestParam String phone, @RequestParam MultipartFile image, ModelMap model){

        try {
            userService.create(name, email, password1, dni, lastName, address, phone, image);
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }

        return "index.html";
    }


}
