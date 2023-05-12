package org.example.service;


import org.example.entity.Provider;

import org.example.repository.ProviderRepository;
import org.example.util.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;


    public void create(String name, String email, String password, String password2, Long Dni, String lastName, String address, Long phone, String image, String description) {

        Provider provider = new Provider();
        provider.setName(name);
        provider.setLastName(lastName);
        provider.setEmail(email);
        provider.setDni(Dni);
        provider.setAddress(address);
        provider.setPhone(phone);
        provider.setImage(image);
        provider.setDescription(description);


        if (password.equals(password2)) {
            provider.setPassword(password);
        }
        provider.setRol(Rol.PROVIDER);


    }

}

