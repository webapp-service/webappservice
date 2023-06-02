package org.example.util;


import org.example.entity.Attendance;
import org.example.entity.Provider;
import org.example.entity.User;
import org.example.repository.ProviderRepository;
import org.example.repository.UserRepository;
import org.example.service.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class Validation {
    @Autowired
    private AttendanceServiceImpl attendanceServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProviderRepository providerRepository;

    public User validationUser(String name, String email, String password, Long dni, String lastName, String address, String phone, MultipartFile image) throws Exception {

        validateName(name);
        validateEmail(email);
        validatePassword(password);
        validateDni(dni);
        validateLastName(lastName);
        validateAddress(address);
        validatePhone(phone);
        validateImage(image);

        User user = createUser(name, email, password, dni, lastName, address, phone, image);

        return user;
    }

    public void validationProviderModify(String name, String lastName, String phone, String address,
                                         String password, String description, Double pricePerHour, MultipartFile image) throws Exception {
        validateName(name);
        validateLastName(lastName);
        validatePhone(phone);
        validateAddress(address);
        validatePassword(password);
        validateDescription(description);
        validatePrice(pricePerHour);
        validateImage(image);
    }

    public Provider validationProvider(String name, String email, String password, Long dni, String lastName, String address, String phone, String description, Double pricePerHour, Integer idAttendance, MultipartFile image) throws Exception {

        validateName(name);
        validateEmail(email);
        validatePassword(password);
        validateDni(dni);
        validateLastName(lastName);
        validateAddress(address);
        validatePhone(phone);
        validateDescription(description);
        validatePrice(pricePerHour);
        validateImage(image);

        Provider provider = createProvider(name, email, password, dni, lastName, address, phone, description, pricePerHour, idAttendance, image);

        return provider;
    }

    private void validateName(String name) throws Exception {
        if (name.isEmpty() || name.length() < 4) {
            throw new Exception("El nombre no puede estar vacío o tener menos de 4 caracteres");
        }
    }

    private void validateEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        Provider provider = providerRepository.findByEmail(email);

        if ((user == null || !user.isActive()) && (provider == null || !provider.isActive())) {


            if (email == null || email.length() <= 10) {

                throw new Exception("El email no puede estar vacío o tener menos de 10 caracteres");
            }
        } else {
            throw new Exception("El email ya existe en la base de datos.");
        }
    }

    private void validatePassword(String password) throws Exception {
        if (password.isEmpty() || password.length() <= 5) {
            throw new Exception("El password no puede estar vacío o tener menos de 5 caracteres");
        }
    }

    private void validateDni(Long dni) throws Exception {
        Optional<User> user = userRepository.findById(dni);
        Optional<Provider> provider = providerRepository.findById(dni);

        if ((!user.isPresent() || !user.get().isActive())
                && (!provider.isPresent() || !provider.get().isActive())) {
            if (dni <= 5999999) {
                throw new Exception("El dni no puede ser menor a 6 millones");
            }

        } else {
            throw new Exception("El dni ya existe en la base de datos");
        }
    }

    private void validateLastName(String lastName) throws Exception {
        if (lastName.isEmpty() || lastName.length() <= 5) {
            throw new Exception("El apellido no puede estar vacío o tener menos de 5 caracteres");
        }
    }

    private void validateAddress(String address) throws Exception {
        if (address.isEmpty() || address.length() <= 7) {
            throw new Exception("La dirección no puede estar vacía o tener menos de 7 caracteres");
        }
    }

    private void validatePhone(String phone) throws Exception {
        if (phone.length() <= 9) {
            throw new Exception("El teléfono no puede tener menos de 10 números");
        }
    }

    private void validateDescription(String description) throws Exception {
        if (description.isEmpty() || description.length() <= 10) {
            throw new Exception("La descripción debe tener más de 10 caracteres");
        }
    }

    private void validatePrice(Double pricePerHour) throws Exception {
        if (pricePerHour <= 0) {
            throw new Exception("El precio debe ser superior a 0.00");
        }
    }

    private void validateImage(MultipartFile image) throws Exception {
        if (image == null || image.isEmpty()) {
            throw new Exception("Debe cargar una imagen");
        }
    }


    public User createUser(String name, String email, String password, Long dni, String lastName, String address, String phone, MultipartFile image) throws Exception {

        User user = new User();

        user.setDni(dni);
        user.setName(name);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAddress(address);


        Path directoryImages = null;
        try {

            if (image == null || image.isEmpty()) {
                directoryImages = Paths.get("src//main//resources/static/images/imagenPredeterminada.jpg");
                user.setImage("imagenPredeterminada.jpg");
            } else {
                directoryImages = Paths.get("src//main//resources/static/images");
                String absolutePath = directoryImages.toFile().getAbsolutePath();
                byte[] byteImg = image.getBytes();
                Path fullPath = Paths.get(absolutePath + "//" + image.getOriginalFilename());
                Files.write(fullPath, byteImg);
                user.setImage(image.getOriginalFilename());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);

        return user;
    }

    private Provider createProvider(String name, String email, String password, Long dni, String lastName, String address, String phone, String description, Double pricePerHour, Integer idAttendance, MultipartFile image) throws Exception {

        Provider provider = new Provider();

        provider.setDni(dni);
        provider.setName(name);
        provider.setLastName(lastName);
        provider.setPhone(phone);
        provider.setEmail(email);
        provider.setAddress(address);

        try {

            Path directoryImages = Paths.get("src//main//resources/static/images");
            String absolutePath = directoryImages.toFile().getAbsolutePath();
            byte[] byteImg = image.getBytes();
            Path fullPath = Paths.get(absolutePath + "//" + image.getOriginalFilename());
            Files.write(fullPath, byteImg);
            provider.setImage(image.getOriginalFilename());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        provider.setPassword(encodedPassword);
        provider.setDescription(description);
        provider.setPricePerHour(pricePerHour);

        Attendance attendance = attendanceServiceImpl.findAttendance(idAttendance).get();
        provider.getAttendances().add(attendance);
        provider.setRole(Role.PROVIDER);

        return provider;
    }
}



/*

@Service
public class Validation {
    @Autowired
    private AttendanceServiceImpl attendanceServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProviderRepository providerRepository;




    public User validationUser(String name, String email, String password, Long dni, String lastName, String address, String phone, MultipartFile image) throws Exception {

        if (!name.isEmpty() && name.length() >= 4) {

            if (userRepository.findByEmail(email) ==null && providerRepository.findByEmail(email) ==null && email.length() > 10) {

                if (!userRepository.existsById(dni) && !providerRepository.existsById(dni )) {


                    if (!password.isEmpty() && password.length() > 5) {

                        if (dni > 5999999) {

                            if (!lastName.isEmpty() && lastName.length() > 5) {

                                if (!address.isEmpty() && address.length() > 7) {

                                    if (phone.length() > 9) {

                                        if (image != null && !image.isEmpty()) {


                                            User user = new User();
                                            user.setDni(dni);
                                            user.setName(name);
                                            user.setLastName(lastName);
                                            user.setPhone(phone);
                                            user.setEmail(email);
                                            user.setAddress(address);
                                            try {

                                                Path directorioImagenes = Paths.get("src//main//resources/static/images");
                                                String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
                                                byte[] byteImg = image.getBytes();
                                                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + image.getOriginalFilename());
                                                Files.write(rutaCompleta, byteImg);
                                                user.setImage(image.getOriginalFilename());


                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }

                                            String encodedPassword = new BCryptPasswordEncoder().encode(password);
                                            user.setPassword(encodedPassword);
                                            user.setRole(Role.USER);

                                            return user;

                                        } else throw new Exception("debe cargar una imagen");

                                    } else throw new Exception("el telefono no puede ser menor a 10 numeros");

                                } else
                                    throw new Exception("la direccion no puede estar nula o con menos de 7 caracteres");

                            } else throw new Exception("el apellido no puede estar nulo o con menos de 5 caracteres");

                        } else throw new Exception("el dni no puede ser menor a 6 millones");

                    } else throw new Exception("el password no puede estar vacio o con menos de 5 caracteres");

                }else throw new Exception("el dni ya existe en la base de datos");
            }else throw new Exception("el email no puede estar vacio o con menos de 10 caracteres");

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

                                            if (image!=null && !image.isEmpty()){

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
*/
