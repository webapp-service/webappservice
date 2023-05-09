package org.example.Service;


import org.example.entity.Admin;
import org.example.repository.AdminRepository;
import org.example.util.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;


    public void registrarAdmin(String nombre, String mail, String password, String password2,Long Dni,String apellido,String direccion,Long telefono) {

        Admin admin= new Admin();
        admin.setNombre(nombre);
        admin.setApellido(apellido);
        admin.setMail(mail);
        admin.setDni(Dni);
        admin.setDireccion(direccion);
        admin.setTelefono(telefono);


        if (password.equals(password2)){
            admin.setPassword(password);
        }
        admin.setRol(Rol.ADMIN);



    }
}
