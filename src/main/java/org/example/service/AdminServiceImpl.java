package org.example.service;

import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.util.Role.ADMIN;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public void rolAdmin(long idUser){

    Optional<User> userOpc = userServiceImpl.GetOneById(idUser);

    if(userOpc.isPresent()){
        User user=userOpc.get();
         user.setRole(ADMIN);
    }


    }
}
