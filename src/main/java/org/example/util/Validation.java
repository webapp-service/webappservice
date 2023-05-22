package org.example.util;


import org.example.entity.Attendance;
import org.example.entity.Provider;
import org.example.entity.User;
import org.example.service.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class Validation {
    @Autowired
    private AttendanceServiceImpl attendanceServiceImpl;


    public User validationUser(String name, String email, String password, Long dni, String lastName, String address, String phone, MultipartFile image) throws Exception {

        if (!name.isEmpty() && name.length() >= 4) {

            if (!email.isEmpty() && email.length() > 10) {

                if (!password.isEmpty() && password.length() > 5) {

                    if (dni > 5999999) {

                        if (!lastName.isEmpty() && lastName.length() > 5) {

                            if (!address.isEmpty() && address.length() > 7) {

                                if (phone.length() > 9) {

                                    if (image != null) {


                                        User user = new User();
                                        user.setDni(dni);
                                        user.setName(name);
                                        user.setLastName(lastName);
                                        user.setPhone(phone);
                                        user.setEmail(email);
                                        user.setAddress(address);
                                        try{

                                            Path directorioImagenes= Paths.get("src//main//resources/static/images");
                                            String rutaAbsoluta=directorioImagenes.toFile().getAbsolutePath();
                                            byte[] byteImg = image.getBytes();
                                            Path rutaCompleta=Paths.get(rutaAbsoluta+"//"+image.getOriginalFilename());
                                            Files.write(rutaCompleta, byteImg);
                                            user.setImage(image.getOriginalFilename());


                                        }catch (Exception e){
                                            System.out.println(e.getMessage());
                                        }

                                        String encodedPassword = new BCryptPasswordEncoder().encode(password);
                                        user.setPassword(encodedPassword);
                                        user.setRole(Role.USER);

                                        return user;

                                    } else throw new Exception("debe cargar una imagen");

                                } else throw new Exception("el telefono no puede ser menor a 10 numeros");

                            } else throw new Exception("la direccion no puede estar nula o con menos de 7 caracteres");

                        } else throw new Exception("el apellido no puede estar nulo o con menos de 5 caracteres");

                    } else throw new Exception("el dni no puede ser menor a 6 millones");

                } else throw new Exception("el password no puede estar vacio o con menos de 5 caracteres");

            } else throw new Exception("el email no puede estar vacio o con menos de 10 caracteres");

        } else throw new Exception("el nombre no puede estar vacio o con menos de 4 caracteres");
    }

    public Provider validationProvider(String name, String email, String password, Long dni, String lastName, String address, String phone, String description, Double pricePerHour, Integer idAttendace,MultipartFile image) throws Exception {

        if (!name.isEmpty() && name.length() >= 4) {

            if (!email.isEmpty() && email.length() > 10) {
//                checkEmail(email);

                if (!password.isEmpty() && password.length() > 5) {

                    if (dni > 5999999) {

                        if (!lastName.isEmpty() && lastName.length() > 5) {

                            if (!address.isEmpty() && address.length() > 7) {

                                if (phone.length() > 9) {

                                    if (!description.isEmpty() && description.length() > 50) {

                                        if (pricePerHour > 0) {

                                            if (image!=null ){

                                                Provider provider = new Provider();
                                                provider.setDni(dni);
                                                provider.setName(name);
                                                provider.setLastName(lastName);
                                                provider.setPhone(phone);
                                                provider.setEmail(email);
                                                provider.setAddress(address);

                                                try{

                                                Path directorioImagenes= Paths.get("src//main//resources/static/images");
                                                String rutaAbsoluta=directorioImagenes.toFile().getAbsolutePath();
                                                byte[] byteImg = image.getBytes();
                                                Path rutaCompleta=Paths.get(rutaAbsoluta+"//"+image.getOriginalFilename());
                                                Files.write(rutaCompleta, byteImg);
                                                provider.setImage(image.getOriginalFilename());


                                                }catch (Exception e){
                                                    System.out.println(e.getMessage());
                                                }

                                                String encodedPassword = new BCryptPasswordEncoder().encode(password);
                                                provider.setPassword(encodedPassword);
                                                provider.setDescription(description);
                                                provider.setPricePerHour(pricePerHour);


                                                Attendance attendance = attendanceServiceImpl.findAttendance(idAttendace).get();

                                                provider.getAttendances().add(attendance);
                                                provider.setRole(Role.PROVIDER);

                                                return provider;


                                            }else{
                                                throw new Exception("debe ingresar una imagen");
                                            }



                                        } else throw new Exception("El precio debe ser superior a 0.00");

                                    } else throw new Exception("La descripcion debe tener mas de 50 caracteres");

                                } else throw new Exception("El telefono no puede ser menor a 10 numeros");

                            } else throw new Exception("La direccion no puede estar nula o con menos de 7 caracteres");

                        } else throw new Exception("El apellido no puede estar nulo o con menos de 5 caracteres");

                    } else throw new Exception("El dni no puede ser menor a 6 millones");

                } else throw new Exception("El password no puede estar vacio o con menos de 5 caracteres");

            } else throw new Exception("El email no puede estar vacio o con menos de 10 caracteres");

        } else throw new Exception("El nombre no puede estar vacio o con menos de 4 caracteres");
    }

}