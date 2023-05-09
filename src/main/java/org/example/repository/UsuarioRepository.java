package org.example.repository;


import org.example.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String> {

    @Query("SELECT u FROM Usuario u WHERE u.mail =:mail")
    public Usuario findByEmail(@Param("mail") String mail);
}
