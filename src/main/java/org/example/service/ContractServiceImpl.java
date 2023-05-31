package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.ContractDTO;
import org.example.entity.Contract;
import org.example.entity.Status;
import org.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRep;
    private final StatusRepository statusRep;
    private final AttendanceRepository attendanceRep;
    private final ProviderRepository providerRep;
    private final UserRepository userRep;

    @Transactional
    @Override
    public void createContract(int attendanceId, Long providerId, Long userId) {
        List<Contract> contracts = contractRep.findByUserAndProviderAndAttendance(userId, providerId, attendanceId);
        boolean flag = true;

        if (!contracts.isEmpty()) {
            for (Contract c : contracts) {
                if (c.getStatus().getId() != 4) {
                    flag = false;
                    break;
                }
            }
        }

        if (flag) {
            Contract contract = new Contract();
            contract.setContractDate(new Date());
            contract.setStatus(statusRep.getById(1));
            contract.setAttendance(attendanceRep.findById(attendanceId).get());
            contract.setProvider(providerRep.findById(providerId).get());
            contract.setScore(0);
            contract.setUser(userRep.findById(userId).get());
            contractRep.save(contract);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Contract getContractById(int contractId) {
        Contract contract = contractRep.findById(contractId).get();
        return contract;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Contract> getAllContracts() {
        List<Contract> contracts = contractRep.findAll();
        return contracts;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Contract> getAllContractsByProvider(Long providerId) {
        List<Contract> contracts = contractRep.findAllByProvider(providerId);
        return contracts;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Contract> getAllContractsByUser(Long userId) {
        List<Contract> contracts = contractRep.findAllByUser(userId);
        return contracts;
    }


    @Override
    public List<Contract> findByProviderAndAttendance(Long userId, Integer attendanceId) {
        List<Contract> contracts = contractRep.findByProviderAndAttendance(userId, attendanceId);
        return contracts;
    }

    @Override
    public List<Contract> findByUserAndAttendance(Long userId, Integer attendanceId) {
        List<Contract> contracts = contractRep.findByUserAndAttendance(userId, attendanceId);
        return contracts;
    }

    @Transactional
    @Override
    public void qualify(int contractId, int score, String comment) throws Exception {
        Contract contract = getContractById(contractId);
//            if (contract.getScore() >= 1 && contract.getComment().length()>0) {
//                throw new Exception("el contrato ya fue comentado y opinado");
//
//            }
        if (contract.getStatus().getId().equals(4)) {
            if (score > 0 && score <= 5) {
                contract.setScore(score);

                if (comment.trim().length() > 10) {
                    contract.setComment(comment);
                    contractRep.save(contract);

                } else throw new Exception("el comentario debe contener mas de 10 caracteres");

            } else throw new Exception("el puntaje debe ser entre 1 y 5");

        } else throw new Exception("el status debe ser completado");
    }

    @Transactional
    @Override
    public void censureComment(int contractId) {
        Contract contract = getContractById(contractId);
        contract.setComment("comentario censurado por un moderador");
        contractRep.save(contract);
    }

    public void statusChange(int idContract, int idStatus) throws Exception {
        Optional<Contract> contractResp = contractRep.findById(idContract);
        Optional<Status> statusResp = statusRep.findById(idStatus);

        if (contractResp.isPresent()) {
            Contract contract = contractResp.get();

            if (statusResp.isPresent()) {
                Status status = statusResp.get();
                contract.setStatus(status);
                contractRep.save(contract);
            } else {
                throw new Exception("no se encontro un estado valido");
            }

        } else {
            throw new Exception("no se encontro el contrato");

        }
    }

    public List<ContractDTO> createContractDTO(Long idPerson) {
        List<Contract> contracts;
        System.out.println(idPerson);
        if (!providerRep.findById(idPerson).isPresent()) {

            contracts = contractRep.findAllByUser(idPerson);
            System.out.println(contracts.size());
        } else {

            contracts = contractRep.findAllByProvider(idPerson);
        }


        List<ContractDTO> contractDTOS = new ArrayList<>();

        Integer contador = 0;

        for (Contract aux : contracts) {
            contador++;
            ContractDTO contractDTO = new ContractDTO();
            contractDTO.setContractId(aux.getContractId());
            contractDTO.setStatus(aux.getStatus());
            contractDTO.setUser(aux.getUser());
            contractDTO.setAttendance(aux.getAttendance());
            contractDTO.setContractDtoId(contador);
            contractDTO.setProvider(aux.getProvider());
            contractDTOS.add(contractDTO);
        }
        System.out.println(contractDTOS.size());
        return contractDTOS;
    }

}


