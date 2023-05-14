package org.example.service;
import org.example.entity.Provider;
import org.example.repository.ProviderRepository;
import org.example.util.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    public void create(String name, String email, String password, String password2, Long Dni, String lastName,
                       String address, Long phone, MultipartFile image, String description) throws IOException {

        Provider provider = new Provider();
        provider.setName(name);
        provider.setLastName(lastName);
        provider.setEmail(email);
        provider.setDni(Dni);
        provider.setAddress(address);
        provider.setPhone(phone);
        provider.setImage(image.getBytes());
        provider.setDescription(description);
        if (password.equals(password2)) {
            provider.setPassword(password);
        }
        provider.setRol(Rol.PROVIDER);
    }

    public List<Provider> providers(){
        List<Provider> providers = new ArrayList<>();
        providers = providerRepository.findAll();
        return providers;
    }
    public void modifyProvider(Long dni, String name, String lastname, Long phone, String email, String address,
                               MultipartFile image, String password, Rol rol, String description, Double pricePerHour) throws IOException {
        Optional<Provider> optProvider = providerRepository.findById(dni);
        if(optProvider.isPresent()){
            Provider provider = optProvider.get();
            provider.setDni(dni);
            provider.setName(name);
            provider.setLastName(lastname);
            provider.setPhone(phone);
            provider.setEmail(email);
            provider.setAddress(address);
            provider.setImage(image.getBytes());
            provider.setPassword(password);
            provider.setRol(rol);
            provider.setDescription(description);
            provider.setPricePerHour(pricePerHour);
            providerRepository.save(provider);
        }
    }
    public void delete(Long dni){
        Optional<Provider> optProvider = providerRepository.findById(dni);
        Provider provider = optProvider.get();
        providerRepository.deleteById(dni);
    }

    public void validate(Long dni, String name, String lastname, Long phone, String email,
                        String addess, String image, String password, Rol rol, String description, Double pricePerHour){
    }

    public Provider getOne(Long dni){
        return providerRepository.findById(dni).get();
    }


}

