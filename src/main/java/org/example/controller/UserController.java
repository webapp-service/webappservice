package org.example.controller;

import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/id/contract")
    public String userMenu(){

        return "user_menu.html";
    }

    @PostMapping("/register")
    public String register(String name, String email, String password1, Long dni, String lastName, String address, String phone, MultipartFile image){

        try {
            userService.create(name, email, password1, dni, lastName, address, phone, image);
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }

        return "index.html";
    }


}
