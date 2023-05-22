package org.example.service;

import org.example.dto.ProviderDTO;
import org.example.entity.Attendance;
import org.example.entity.Contract;
import org.example.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderDTOServiceImpl implements ProviderDTOService{

    @Autowired
    ProviderServiceImpl providerServiceImpl;
    @Autowired
    ContractServiceImpl contractServiceImpl;
    @Autowired
    AttendanceServiceImpl attendanceservice;


    @Override
    public List<ProviderDTO> create() {
        List<Provider> providers = providerServiceImpl.providers();
        ArrayList<ProviderDTO> providersDTO = new ArrayList<>();
        Integer id = 0;
        for (Provider p : providers) {

            List<Attendance> attendances = p.getAttendances();

            for (Attendance a : attendances) {
                id++;
                ProviderDTO providerDTO = new ProviderDTO();
                providerDTO.setName(p.getName());
                providerDTO.setLastName(p.getLastName());
                providerDTO.setPhone(p.getPhone());
                providerDTO.setEmail(p.getEmail());
                providerDTO.setAddress(p.getAddress());
                providerDTO.setImage(p.getImage());
                providerDTO.setPassword(p.getPassword());
                providerDTO.setRole(p.getRole());
                providerDTO.setAttendanceUnique(a);
                providerDTO.setScore(averageScore(p.getDni(), a.getId()));
                providersDTO.add(providerDTO);
                providerDTO.setIdDto(id);            }
        }
        return providersDTO;
    }


    @Override
    public Optional<Attendance> getAttendances(Integer idAttendance) {
        Optional<Attendance> attendance = attendanceservice.findAttendance(idAttendance);
        return attendance;
    }


    @Override
    public Integer averageScore(long idProvider, Integer idAttendance) {
        List<Contract> contract = contractServiceImpl.findByProviderAndAttendance(idProvider, idAttendance);
        Integer score = 0;
        Integer count = 0;
        Integer average = 0;

        if (contract.size() > 0) {
            for (Contract contractAux : contract) {
                if ((contractAux.getScore() > 0) && (contractAux.getScore() != null)) {
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

}
