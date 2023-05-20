package org.example.service;

import org.example.entity.User;

import org.example.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.util.Role.ADMIN;

@Service
public class AdminService {

    @Autowired
    UserService userService;

    public void rolAdmin(long idUser){

    Optional<User> userOpc = userService.GetOneById(idUser);

    if(userOpc.isPresent()){
        User user=userOpc.get();
         user.setRole(ADMIN);
    }


    }
}
