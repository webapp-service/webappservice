package org.example.service;

import org.example.entity.Provider;
import org.example.repository.ProviderRepository;
import org.example.repository.UserRepository;
import org.example.util.Role;
import org.example.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private AttendanceServiceImpl attendanceServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(String name, String email, String password, Long dni, String lastName,
                       String address, String phone, String description, Double pricePerHour, Integer idAttendance, MultipartFile image) throws Exception {

        try {
            if (getOne(dni) == null && !userRepository.findById(dni).isPresent()) {

                Provider provider = validation.validationProvider(name, email, password, dni, lastName, address, phone, description, pricePerHour, idAttendance,image);
                providerRepository.save(provider);
            } else {
                throw new Exception("Error:  El dni ya se encuentra registrado en la base de datos");
            }
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Error: El email ya esta registrado en la base de datos", e);

    }

    }
    @Override
    public List<Provider> providers() {
        List<Provider> providers = new ArrayList<>();
        providers = providerRepository.findAll();
        return providers;
    }


    @Override
    public void modifyProvider(Long dni, String name, String lastName, String phone, String email, String address,
                               String password, Role role, String description, Double pricePerHour, Integer idAttendance, MultipartFile image) throws Exception {

        Provider provider = validation.validationProvider(name, email, password, dni, lastName, address, phone, description, pricePerHour, idAttendance,image);
        if (provider != null) {
            /*String encodedPassword = new BCryptPasswordEncoder().encode(password);
            provider.setPassword(encodedPassword);*/
            providerRepository.save(provider);
        }


    }

    @Override
    public void delete(Long dni) {
        Optional<Provider> optProvider = providerRepository.findById(dni);
        Provider provider = optProvider.get();
        providerRepository.deleteById(dni);
    }

    @Override
    public Provider getOne(Long dni) {
        Optional<Provider> optProvider = providerRepository.findById(dni);

        if (optProvider.isPresent()) {
            return optProvider.get();
        } else {
            return null;

        }
    }

    @Override
    public Provider getByEmail(String email) {
        return providerRepository.findByEmail(email);
    }
}

