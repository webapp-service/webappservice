package org.example.repository;

import org.example.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Long> {

    @Query("SELECT u FROM Provider u WHERE u.email =:email")
    public Provider findByEmail(@Param("email") String email);
}
