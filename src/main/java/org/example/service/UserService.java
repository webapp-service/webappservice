package org.example.service;

import org.example.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void create(String name, String email, String password, Long Dni, String lastName, String address, String phone, MultipartFile image) throws Exception;

    List<User> getUsers();

    void modify(Long dni, String name, String lastName, String phone, String address,
                MultipartFile image, String password) throws Exception;

    void setStatus(Long dni);

    User getOneByEmail(String email);

    Optional<User> getOneById(long id);

    void modify(User user);
}
