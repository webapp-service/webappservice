package org.example.service;
import org.example.entity.Provider;
import org.example.repository.ProviderRepository;
import org.example.util.Role;
import org.example.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private Validation validation;

    public void create(String name, String email, String password, Long dni, String lastName,
                       String address, Long phone, MultipartFile image, String description, Double pricePerHour) throws Exception {

        Provider provider = validation.validationProvider(name,email,password,dni,lastName,address,phone,image,description,pricePerHour);
        if (provider !=null) {

            providerRepository.save(provider);
        }
    }

    public ArrayList<Provider> providers(){
        List<Provider> providers = new ArrayList<>();
        providers = providerRepository.findAll();
        return providers;
    }
    public void modifyProvider(Long dni, String name, String lastName, Long phone, String email, String address,
                               MultipartFile image, String password, Role role, String description, Double pricePerHour) throws Exception {

        Provider provider = providerRepository.findById(dni).orElseThrow(()->new EntityNotFoundException("no se encontro el id"));

        Provider provider1 = validation.validationProvider(name,email,password,dni,lastName,address,phone,image,description,pricePerHour);
        if (provider !=null) {

            providerRepository.save(provider1);
        }


        }

    public void delete(Long dni){
        Optional<Provider> optProvider = providerRepository.findById(dni);
        Provider provider = optProvider.get();
        providerRepository.deleteById(dni);
    }

    public void validate(Long dni, String name, String lastname, Long phone, String email,
                         String addess, String image, String password, Role role, String description, Double pricePerHour){
    }

    public Provider getOne(Long dni){
        return providerRepository.findById(dni).get();
    }


}

