package org.example.repository;

import org.example.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin,String> {

    @Query("SELECT u FROM Admin u WHERE u.mail =:mail")
    public Admin findByEmail(@Param("mail") String mail);
}
