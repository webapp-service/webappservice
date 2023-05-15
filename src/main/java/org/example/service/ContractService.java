package org.example.service;

import lombok.AllArgsConstructor;
import org.example.entity.Contract;
import org.example.entity.Status;
import org.example.entity.User;
import org.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ContractService {
    private final ContractRepository contractRep;
    private final StatusRepository statusRep;
    private final AttendanceRepository attendanceRep;
    private final ProviderRepository providerRep;
    private final UserRepository userRep;

    @Transactional
    public void createContract(int attendanceId, Long providerId, Long userId) {
        Contract contract = new Contract();
        contract.setContractDate(new Date());
        contract.setStatus(statusRep.findByName("PENDING"));
        contract.setAttendance(attendanceRep.findById(attendanceId).get());
        contract.setProvider(providerRep.findById(providerId).get());
        contract.setUser(userRep.findById(userId).get());
        contractRep.save(contract);
    }

    @Transactional(readOnly = true)
    public Contract getContractById(int contractId) {
        Contract contract = contractRep.findById(contractId).get();
        return contract;
    }

    @Transactional(readOnly = true)
    public List<Contract> getAllContracts() {
        List<Contract> contracts = contractRep.findAll();
        return contracts;
    }

    @Transactional(readOnly = true)
    public List<Contract> getAllContractsByProvider(Long providerId) {
        List<Contract> contracts = contractRep.findAllByProvider(providerId);
        return contracts;
    }

    @Transactional(readOnly = true)
    public List<Contract> getAllContractsByUser(Long userId) {
        List<Contract> contracts = contractRep.findAllByProvider(userId);
        return contracts;
    }

    @Transactional
    public void changeContractStatus(int contractId, String contractStatus) {
        Contract contract = getContractById(contractId);
        Status status = statusRep.findByName(contractStatus);
        String actualStatusName = contract.getStatus().getName();

        if (validateContractStatusChange(contractStatus, actualStatusName)) {
            contract.setStatus(status);
            contractRep.save(contract);
        }
    }

    @Transactional
    public void qualify(int contractId, Long userId, int score, String comment) {
        Contract contract = getContractById(contractId);
        User user = userRep.findById(userId).get();

        if (contract.getUser().equals(user)) {
            String statusName = contract.getStatus().getName();

            if (statusName.equals("CANCELLED") || statusName.equals("COMPLETED")) {
                contract.setScore(score);
                contract.setComment(comment);
                contractRep.save(contract);
            }
        }
    }

    private boolean validateContractStatusChange(String actualStatusName, String contractStatus) {
        if (actualStatusName.equals("PENDING")) {
            return (contractStatus.equals("IN PROGRESS") || contractStatus.equals("CANCELLED"));
        }
        if (actualStatusName.equals("IN PROGRESS")) {
            return contractStatus.equals("CANCELLED") || contractStatus.equals("COMPLETED");
        }
        return false;
    }
}


