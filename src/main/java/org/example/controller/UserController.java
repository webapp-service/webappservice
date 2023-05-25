package org.example.controller;

import org.example.entity.Person;
import org.example.service.ContractServiceImpl;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    ContractServiceImpl contractService;

    @GetMapping("/profile")
    public String userMenu(HttpSession httpSession, ModelMap model) {
        Person logged = (Person) httpSession.getAttribute("usersession");
        long loggedDni = logged.getDni();

        model.addAttribute("user", userService.GetOneById(loggedDni));
        model.addAttribute("userContracts", contractService.getAllContractsByUser(loggedDni));

        return "user_menu";
    }


    @GetMapping("/register")
    public String register() {
        return "user_form";

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
            return "user_form";
        }

        return "redirect:/";
    }

    }





