package org.example.service;

import org.example.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void create(String name, String email, String password, Long Dni, String lastName, String address, String phone, MultipartFile image) throws Exception;

    List<User> GetUsers();

    void modify(Long dni, String name, String lastName, String phone, String email, String address,
                MultipartFile image, String password) throws Exception;

    void delete(Long DNI);

    User GetOneByEmail(String email);

    Optional<User> GetOneById(long id);

    void modify(User user);
}
