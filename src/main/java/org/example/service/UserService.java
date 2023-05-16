package org.example.service;

import org.example.entity.Provider;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.util.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(String name, String email, String password, String password2, Long Dni, String lastName, String address, Long phone, MultipartFile image) throws IOException {

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setDni(Dni);
        user.setAddress(address);
        user.setPhone(phone);
        user.setImage(image.getBytes());

        if (password.equals(password2)) {
            user.setPassword(password);
        }
        user.setRol(Rol.USER);


    }

    public List<User> GetUsers() {
        List<User> users = new ArrayList<User>();
        users = userRepository.findAll();
        return users;
    }

    public void modify(Long dni, String name, String lastname, Long phone, String email, String address,
                               MultipartFile image, String password, Rol rol) throws IOException {
        Optional<User> optUser = userRepository.findById(dni);
        if(optUser.isPresent()){
            User user = optUser.get();
            user.setDni(dni);
            user.setName(name);
            user.setLastName(lastname);
            user.setPhone(phone);
            user.setEmail(email);
            user.setAddress(address);
            user.setImage(image.getBytes());
            user.setPassword(password);
            user.setRol(rol);
            userRepository.save(user);
        }
    }

    public void delete(Long DNI){
        Optional<User> optUser = userRepository.findById(DNI);
        User user = optUser.get();
        userRepository.deleteById(DNI);
    }

    public User GetOne(String email) {
        return userRepository.findByEmail(email);
    }

}