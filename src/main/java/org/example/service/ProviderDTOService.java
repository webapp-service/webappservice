package org.example.service;

import org.example.dto.ProviderDTO;
import org.example.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderDTOService {

    @Autowired
    ProviderService providerService;
    @Autowired
    ContractService contractService;


    public ProviderDTO create(long dni){

        ProviderDTO providerDTO = (ProviderDTO) providerService.getOne(dni);

        return providerDTO;
    }

    public Integer averageScore(long provider){

        List<Contract> contract = contractService.getAllContractsByProvider(provider);
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
