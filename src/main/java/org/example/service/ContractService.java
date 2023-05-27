package org.example.service;

import org.example.entity.Contract;

import java.util.List;

public interface ContractService {
    void createContract(int attendanceId, Long providerId, Long userId);

    Contract getContractById(int contractId);

    List<Contract> getAllContracts();

    List<Contract> getAllContractsByProvider(Long providerId);

    List<Contract> getAllContractsByUser(Long userId);

    List<Contract> findByProviderAndAttendance(Long userId, Integer attendanceId);

    void qualify(int contractId, int score, String comment) throws Exception;
    List<Contract> findByUserAndAttendance(Long userId, Integer attendanceId);

    void censureComment(int contractId);
}
