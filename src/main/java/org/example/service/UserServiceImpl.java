package org.example.service;


import org.example.entity.Provider;
import org.example.entity.User;
import org.example.repository.ProviderRepository;
import org.example.repository.UserRepository;
import org.example.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    Validation validation;

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public void create(String name, String email, String password, Long Dni, String lastName, String address, String phone, MultipartFile image) throws Exception {

        try {


        if (GetOneById(Dni)==null &&  providerRepository.findById(Dni).isPresent()){

            User user = validation.validationUser(name, email, password, Dni, lastName, address, phone,image);
            userRepository.save(user);
        } else {
            throw new Exception("Error:  El dni ya se encuentra registrado en la base de datos");
        }
    } catch (
    DataIntegrityViolationException e) {
        throw new Exception("Error: El email ya esta registrado en la base de datos", e);
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }

}


    @Override
    public List<User> GetUsers() {
        List<User> users = new ArrayList<User>();
        users = userRepository.findAll();
        return users;
    }

    @Override
    public void modify(Long dni, String name, String lastName, String phone, String email, String address,
                       MultipartFile image, String password) throws Exception {

        Optional<User> userOpc = GetOneById(dni);

        if (userOpc.isPresent()) {
            User useResp = userOpc.get();

            User userAux=validation.validationUser(name, email, password, dni, lastName, address, phone, image);

            if (!(userAux == null)) {
                useResp= userAux;

                userRepository.save(useResp);
            }
        }else {
            throw new Exception("no se encontro el usuario");
        }
    }

    @Override
    public void delete(Long DNI) {
        Optional<User> optUser = userRepository.findById(DNI);
        User user = optUser.get();
        userRepository.deleteById(DNI);
    }

    @Override
    public User GetOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> GetOneById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void modify(User user) {
        userRepository.save(user);
        }

}