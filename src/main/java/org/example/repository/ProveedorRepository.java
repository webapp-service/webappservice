package org.example.repository;

import org.example.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProveedorRepository extends JpaRepository<Proveedor,String> {

    @Query("SELECT u FROM Proveedor u WHERE u.mail =:mail")
    public Proveedor findByEmail(@Param("mail") String mail);
}
