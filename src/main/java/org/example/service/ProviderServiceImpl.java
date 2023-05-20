package org.example.service;

import org.example.entity.Provider;
import org.example.repository.ProviderRepository;
import org.example.util.Role;
import org.example.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService{

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private AttendanceServiceImpl attendanceServiceImpl;

    @Override
    public void create(String name, String email, String password, Long dni, String lastName,
                       String address, String phone, String description, Double pricePerHour, Integer idAttendance) throws Exception {

        Provider provider = validation.validationProvider(name, email, password, dni, lastName, address, phone, description, pricePerHour, idAttendance);
        if (provider != null) {
            String encodedPassword = new BCryptPasswordEncoder().encode(password);
            provider.setPassword(encodedPassword);
            providerRepository.save(provider);
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
                               String password, Role role, String description, Double pricePerHour, Integer idAttendance) throws Exception {

        Provider provider = providerRepository.findById(dni).orElseThrow(() -> new EntityNotFoundException("no se encontro el id"));

        Provider provider1 = validation.validationProvider(name, email, password, dni, lastName, address, phone, description, pricePerHour, idAttendance);
        if (provider != null) {

            providerRepository.save(provider1);
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
        return providerRepository.findById(dni).get();
    }

    @Override
    public Provider getByEmail(String email) {
        return providerRepository.findByEmail(email);
    }
}

