package org.example.Service;



import org.example.entity.Proveedor;

import org.example.repository.ProveedorRepository;
import org.example.util.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;


    public void registrarProveedor(String nombre, String mail, String password, String password2,Long Dni,String apellido,String direccion,Long telefono) {

        Proveedor proveedor= new Proveedor();
        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setMail(mail);
        proveedor.setDni(Dni);
        proveedor.setDireccion(direccion);
        proveedor.setTelefono(telefono);


        if (password.equals(password2)){
            proveedor.setPassword(password);
        }
        proveedor.setRol(Rol.PROOVEDOR);



    }

}

