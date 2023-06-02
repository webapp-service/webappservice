package org.example.service;


import org.example.entity.User;
import org.example.repository.ProviderRepository;
import org.example.repository.UserRepository;
import org.example.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    Validation validation;

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public void create(String name, String email, String password, Long Dni, String lastName, String address, String phone, MultipartFile image) throws Exception {

        User user = validation.createUser(name, email, password, Dni, lastName, address, phone, image);
        userRepository.save(user);


    }


    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        users = userRepository.findAll();
        return users;
    }

    @Override
    public void modify(Long dni, String name, String lastName, String phone, String address,
                       MultipartFile image, String password) throws Exception {
        validation.validationUserModify(name, password, lastName, address, phone, image);
        Optional<User> optUser = getOneById(dni);

        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setName(name);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setAddress(address);

            String encodedPassword = new BCryptPasswordEncoder().encode(password);
            user.setPassword(encodedPassword);

            Path directoryImages = Paths.get("src//main//resources/static/images");
            String absolutePath = directoryImages.toFile().getAbsolutePath();
            byte[] byteImg = image.getBytes();
            Path fullPath = Paths.get(absolutePath + "//" + image.getOriginalFilename());
            Files.write(fullPath, byteImg);
            user.setImage(image.getOriginalFilename());

            userRepository.save(user);
        }

    }

    @Override
    public void setStatus(Long dni) {
        Optional<User> optUser = userRepository.findById(dni);
        User user = optUser.get();
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public User getOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getOneById(long dni) {
        Optional<User> optUser = userRepository.findById(dni);
        if (optUser.isPresent()) {
            User user = optUser.get();
        } else {
            return null;
        }
        return optUser;
    }

    @Override
    public void modify(User user) {
        userRepository.save(user);
    }

}