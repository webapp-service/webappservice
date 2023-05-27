package org.example.service;

import org.example.entity.Contract;
import org.example.entity.Provider;
import org.example.util.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProviderService {
    void create(String name, String email, String password, Long dni, String lastName,
                String address, String phone, String description, Double pricePerHour, Integer idAttendance ,MultipartFile image) throws Exception;

    List<Provider> providers();

    void modifyProvider(Long dni, String name, String lastName, String phone, String email, String address,
                        String password, Role role, String description, Double pricePerHour, Integer idAttendance, MultipartFile image) throws Exception;

    void delete(Long dni);

    Provider getOne(Long dni);

    Provider getByEmail(String email);
    Integer averageScore(long idProvider, Integer idAttendance);
    Integer averageScoreVermas(List<Contract> contracts);
}
