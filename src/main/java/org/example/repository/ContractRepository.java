package org.example.repository;

import org.example.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    @Query("SELECT c FROM Contract c WHERE c.provider.dni = :providerId")
    List<Contract> findAllByProvider(@Param("providerId") Long providerId);
    @Query("SELECT c FROM Contract c WHERE c.user.dni = :userId")
    List<Contract> findAllByUser(@Param("userId") Long userId);
}
