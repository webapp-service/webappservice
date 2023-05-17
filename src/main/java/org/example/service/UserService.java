package org.example.service;


import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    Validation validation;

    public void create(String name, String email, String password, Long Dni, String lastName, String address, Long phone, MultipartFile image) throws Exception {

        User user = validation.validationUser(name, email, password, Dni, lastName, address, phone, image);

        if (!(user == null)) {

            userRepository.save(user);
        }
    }

    public List<User> GetUsers() {
        List<User> users = new ArrayList<User>();
        users = userRepository.findAll();
        return users;
    }

    public void modify(Long dni, String name, String lastName, Long phone, String email, String address,
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

    public void delete(Long DNI) {
        Optional<User> optUser = userRepository.findById(DNI);
        User user = optUser.get();
        userRepository.deleteById(DNI);
    }

    public User GetOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> GetOneById(long id) {
        return userRepository.findById(id);
    }

    public void modify(User user) {
        userRepository.save(user);
        }

}