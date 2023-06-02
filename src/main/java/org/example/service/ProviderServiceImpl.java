package org.example.service;

import org.example.entity.Attendance;
import org.example.entity.Contract;
import org.example.entity.Provider;
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
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private AttendanceServiceImpl attendanceServiceImpl;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContractServiceImpl contractService;


    @Override
    public void create(String name, String email, String password, Long dni, String lastName,
                       String address, String phone, String description, Double pricePerHour, Integer idAttendance, MultipartFile image) throws Exception {

        Provider provider = validation.validationProvider(name, email, password, dni, lastName, address, phone, description, pricePerHour, idAttendance, image);
        providerRepository.save(provider);
    }

    @Override
    public List<Provider> providers() {
        List<Provider> providers = new ArrayList<>();
        providers = providerRepository.findAll();
        return providers;
    }


    @Override
    public void modifyProvider(Long dni, String name, String lastName, String phone, String address,
                               String password, String description, Double pricePerHour, Integer idAttendance, MultipartFile image) throws Exception {
        validation.validationProviderModify(name, lastName, phone, address, password, description, pricePerHour, image);
        Provider provider = getOne(dni);
        List<Attendance> attendances = new ArrayList<>();
        Optional<Attendance> attendance = attendanceServiceImpl.findAttendance(idAttendance);
        if (attendance.isPresent()){
            attendances.add(attendance.get());
        }
        provider.setName(name);
        provider.setLastName(lastName);
        provider.setPhone(phone);
        provider.setAddress(address);

        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        provider.setPassword(encodedPassword);

        provider.setDescription(description);
        provider.setPricePerHour(pricePerHour);
        provider.setAttendances(attendances);

        Path directoryImages = Paths.get("src//main//resources/static/images");
        String absolutePath = directoryImages.toFile().getAbsolutePath();
        byte[] byteImg = image.getBytes();
        Path fullPath = Paths.get(absolutePath + "//" + image.getOriginalFilename());
        Files.write(fullPath, byteImg);
        provider.setImage(image.getOriginalFilename());

        providerRepository.save(provider);

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

    public Integer averageScore(long idProvider, Integer idAttendance) {
        List<Contract> contract = contractService.findByUserAndAttendance(idProvider, idAttendance);
        Integer score = 0;
        Integer count = 0;
        Integer average = 0;
        if (contract.size() > 0) {
            for (Contract contractAux : contract) {
                if ((contractAux.getScore() > 0)) {
                    count++;
                    score = score + contractAux.getScore();
                }
            }
            average = Math.round(score / count);
        } else {
            average = score;
        }

        return average;
    }
    @Override
    public Integer averageScoreVermas(List<Contract> contracts) {
        List<Contract> contracts1 = contracts;
        Integer score = 0;
        double count = 0;
        Integer average = 0;
        if (contracts1.size() > 0) {
            for (Contract contractAux : contracts1) {
                if ((contractAux.getScore() > 0)) {
                    count++;
                    score = score + contractAux.getScore();
                }
            }
            double resultado = Math.ceil(score / count);
            average = (int) resultado;
        } else {
            average = score;
        }

        return average;
    }
}

