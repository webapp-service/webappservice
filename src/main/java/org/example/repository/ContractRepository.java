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

    @Query("SELECT c FROM Contract c WHERE c.user.dni = :userId AND c.attendance.id=:attendanceId")
    List<Contract> findByProviderAndAttendance(@Param("userId") Long userId,@Param("attendanceId") Integer attendanceIdId);

    @Query("SELECT c FROM Contract c WHERE c.provider.dni = :providerId AND c.attendance.id=:attendanceId")
    List<Contract> findByUserAndAttendance(@Param("providerId") Long providerId,@Param("attendanceId") Integer attendanceIdId);

    @Query("SELECT c FROM Contract c WHERE c.user.dni = :userId AND c.provider.dni = :providerId AND c.attendance.id = :attendanceId")
    Contract findByUserAndProviderAndAttendance(@Param("userId") Long userId, @Param("providerId") Long providerId, @Param("attendanceId") Integer attendanceId);
}
