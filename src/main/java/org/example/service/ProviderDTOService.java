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
public class ProviderDTOService {

    @Autowired
    ProviderService providerService;
    @Autowired
    ContractService contractService;
    @Autowired
    AttendanceService attendanceservice;


    public List<ProviderDTO> create(){
        ArrayList<Provider> providers = providerService.providers();
        ArrayList<ProviderDTO> providersDTO = new ArrayList<>();
        for (Provider p: providers) {
            ProviderDTO providerDTO = (ProviderDTO) p;
            List<Attendance> attendances = p.getAttendances();
            for (Attendance a: attendances) {
                providerDTO.setAttendanceUnique(a);
                providerDTO.setScore(averageScore(p.getDni(),a.getId()));
                providersDTO.add(providerDTO);
            }
        }
        return providersDTO;
    }
    public Optional<Attendance> getAttendances(Integer idAttendance){
        Optional<Attendance> attendance = attendanceservice.findAttendance(idAttendance);
        return attendance;
    }
    public Integer averageScore(long idProvider, Integer idAttendance){
        List<Contract> contract = contractService.findByProviderAndAttendance(idProvider,idAttendance);
        Integer score = 0;
        Integer count = 0;
        for(Contract contractAux : contract){
            if((contractAux.getScore() > 0) && (contractAux.getScore() != null)){
                count++;
                score = score + contractAux.getScore();
            }
        }
        Integer average = Math.round(score/count);

        return average;
    }

}
