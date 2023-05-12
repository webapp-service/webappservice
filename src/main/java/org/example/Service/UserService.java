package org.example.Service;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.util.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void create(String name, String email, String password, String password2, Long Dni, String lastName, String address, Long phone, String image) {

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setDni(Dni);
        user.setAddress(address);
        user.setPhone(phone);
        user.setImage(image);


        if (password.equals(password2)) {
            user.setPassword(password);
        }
        user.setRol(Rol.USER);


    }


}
