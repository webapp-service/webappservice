package org.example.Service;

import org.example.entity.Usuario;
import org.example.repository.UsuarioRepository;
import org.example.util.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public void registrarUsuario(String nombre, String mail, String password, String password2,Long Dni,String apellido,String direccion,Long telefono) {

        Usuario usuario= new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setDni(Dni);
        usuario.setDireccion(direccion);
        usuario.setTelefono(telefono);


        if (password.equals(password2)){
            usuario.setPassword(password);
        }
        usuario.setRol(Rol.USUARIO);



    }


}
