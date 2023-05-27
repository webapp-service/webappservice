package org.example.service;

import lombok.AllArgsConstructor;
import org.example.entity.Contract;
import org.example.entity.Status;
import org.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Contract contract = new Contract();
        contract.setContractDate(new Date());
        contract.setStatus(statusRep.getById(1));
        contract.setAttendance(attendanceRep.findById(attendanceId).get());
        contract.setProvider(providerRep.findById(providerId).get());
        contract.setScore(0);
        System.out.println(userRep.findById(userId).get().getName());
        contract.setUser(userRep.findById(userId).get());
        contractRep.save(contract);
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

    @Transactional
    @Override
    public void qualify(int contractId, int score, String comment) throws Exception {
        Contract contract = getContractById(contractId);

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
}


