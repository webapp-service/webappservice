package org.example.util;


import org.example.entity.Attendance;
import org.example.entity.Contract;
import org.example.entity.Provider;
import org.example.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class Validation {

    public User validationUser(String name, String email, String password, Long dni, String lastName, String address, Long phone, MultipartFile image) throws Exception {
        User user = null;

        if (!name.isEmpty() && name.length() >= 4) {

            if (!email.isEmpty() && email.length() > 10) {

                if (!password.isEmpty() && password.length() < 5) {

                    if (dni > 5999999) {
                        if (!lastName.isEmpty() && lastName.length() > 5) {

                            if (address.isEmpty() && address.length()>7){
                                if (phone<999999999){

                                    if(image != null){
                                        user=new User();
                                        user.setDni(dni);
                                        user.setName(name);
                                        user.setLastName(lastName);
                                        user.setPhone(phone);
                                        user.setEmail(email);
                                        user.setAddress(address);
                                        user.setImage(image.getBytes());
                                        user.setPassword(password);
                                        user.setRole(Role.USER);

                                    }else {
                                        throw new Exception("debe cargar una imagen");
                                    }

                                }else {
                                    throw new Exception("el telefono no puede ser menor a 10 numeros");
                                }

                            }else {
                                throw new Exception("la direccion no puede estar nula o con menos de 7 caracteres");
                            }

                        } else {
                            throw new Exception("el apellido no puede estar nulo o con menos de 5 caracteres");
                        }

                    } else {
                        throw new Exception("el dni no puede ser menor a 6 millones");
                    }

                } else {
                    throw new Exception("el password no puede estar vacio o con menos de 5 caracteres");
                }
            } else {
                throw new Exception("el email no puede estar vacio o con menos de 10 caracteres");
            }

        } else {
            throw new Exception("el nombre no puede estar vacio o con menos de 4 caracteres");

        }
        return user;
    }

    public Provider validationProvider (String name, String email, String password, Long dni, String lastName, String address, Long phone, MultipartFile image,String description, Double pricePerHour, Integer idAttendace, Integer idCOntract) throws Exception {

       Provider provider = null;

        if (!name.isEmpty() && name.length() >= 4) {

            if (!email.isEmpty() && email.length() > 10) {

                if (!password.isEmpty() && password.length() < 5) {

                    if (dni > 5999999) {
                        if (!lastName.isEmpty() && lastName.length() > 5) {

                            if (!address.isEmpty() && address.length()>7){

                                if (phone<999999999){

                                    if(image != null){

                                        if(!description.isEmpty() && description.length()>50){


                                            if(pricePerHour >0){
                                                provider = new Provider();
                                                provider.setDni(dni);
                                                provider.setName(name);
                                                provider.setLastName(lastName);
                                                provider.setPhone(phone);
                                                provider.setEmail(email);
                                                provider.setAddress(address);
                                                provider.setImage(image.getBytes());
                                                provider.setPassword(password);
                                                provider.setDescription(description);
                                                provider.setPricePerHour(pricePerHour);
                                                Attendance attendance = new Attendance();
                                                attendance.setId(idAttendace);
                                                provider.getAttendances().add(attendance);
                                                Contract contract = new Contract();
                                                contract.setContractId(idCOntract);
                                                provider.getContracts().add(contract);
                                                provider.setRole(Role.PROVIDER);

                                            }else {
                                                throw new Exception("El precio debe ser superior a 0.00");
                                            }

                                        }else {
                                            throw new Exception("La descripcion debe tener mas de 50 caracteres");
                                        }


                                    }else {
                                        throw new Exception("Debe cargar una imagen");
                                    }

                                }else {
                                    throw new Exception("El telefono no puede ser menor a 10 numeros");
                                }

                            }else {
                                throw new Exception("La direccion no puede estar nula o con menos de 7 caracteres");
                            }

                        } else {
                            throw new Exception("El apellido no puede estar nulo o con menos de 5 caracteres");
                        }

                    } else {
                        throw new Exception("El dni no puede ser menor a 6 millones");
                    }

                } else {
                    throw new Exception("El password no puede estar vacio o con menos de 5 caracteres");
                }
            } else {
                throw new Exception("El email no puede estar vacio o con menos de 10 caracteres");
            }

        } else {
            throw new Exception("El nombre no puede estar vacio o con menos de 4 caracteres");

        }
        return provider;
    }

}