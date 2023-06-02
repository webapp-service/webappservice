package org.example.controller;

import org.example.entity.Attendance;
import org.example.entity.Contract;
import org.example.entity.Person;
import org.example.service.AttendanceService;
import org.example.service.ContractServiceImpl;
import org.example.service.ProviderService;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    ContractServiceImpl contractService;
    @Autowired
    ProviderService providerService;
    @Autowired
    AttendanceService attendanceService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/profile")
    public String userMenu(HttpSession httpSession, ModelMap model) {
        Person logged = (Person) httpSession.getAttribute("usersession");
        long loggedDni = logged.getDni();

        model.addAttribute("logged", logged.getName());
        model.addAttribute("rol", logged.getRole());
        model.addAttribute("user", logged);
        model.addAttribute("userContracts", contractService.createContractDTO(loggedDni));

        return "user_menu";
    }

    @GetMapping("/register")
    public String register() {
        return "user_form";

    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email,
                           @RequestParam String password1, @RequestParam Long dni,
                           @RequestParam String lastName, @RequestParam String address,
                           @RequestParam String phone, @RequestParam MultipartFile image, ModelMap model) {

        try {
            userService.create(name, email, password1, dni, lastName, address, phone, image);
        } catch (Exception e) {
            model.put("error", e.getMessage());


            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("dni", dni);
            model.addAttribute("lastName", lastName);
            model.addAttribute("address", address);
            model.addAttribute("phone", phone);
            model.addAttribute("image", image);


            return "user_form";
        }

        return "redirect:/login";
    }

    @PostMapping("/rate/{contractId}")
    public String rateAndComment(@PathVariable int contractId, @RequestParam int rate, @RequestParam String comment,
                                 ModelMap model) {
        try {
            contractService.qualify(contractId, rate, comment);
        } catch (Exception e) {
            model.put("contractId", contractId);
            model.put("rate", rate);
            model.put("comment", comment);
            model.put("error", e.getMessage());

            return "rate_provider";
        }
        return "redirect:/user/profile";
    }

    @GetMapping("/rate/{contractId}")
    public String CommentCompletedContract(@PathVariable Integer contractId, ModelMap model) {
        model.put("contractId", contractId);
        return "rate_provider";
    }

    @PostMapping("/menu_cancel")
    public String CancelContract(Integer contractId) {
        try {
            contractService.statusChange(contractId, 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "user_menu";
    }


    @GetMapping("/{contractId}/{buttonId}")
    public String buttonAction(@PathVariable Integer contractId, @PathVariable Integer buttonId, ModelMap model, HttpSession httpSession) {

        // si el boton es 1, devuelve la vista para comentar + calificar, si es 2, es para cancelar el contrato
        Contract contract = contractService.getContractById(contractId);
        Person logged = (Person) httpSession.getAttribute("usersession");
        Long userId = logged.getDni();
        switch (buttonId) {
            case 1:
                model.addAttribute("contract", contract);
                return "rate_provider";

            case 2: //Llamar al metodo para cancelar contrato?
                try {
                    contractService.statusChange(contractId, 4);
                } catch (Exception e) {
                    //throw new RuntimeException(e);
                    System.out.println(e.getMessage());

                }
                if (logged != null) {
                    long loggedDni = logged.getDni();
                    model.addAttribute("logged", logged.getName());
                    model.addAttribute("rol", logged.getRole());
                    model.addAttribute("user", logged);
                    model.addAttribute("userContracts", contractService.getAllContractsByUser(loggedDni));
                } else {
                    return "login";
                }
                return "redirect:/user/profile";
        }
        return "redirect:/user/profile";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/changeToProvider")
    public String changeToProvider(ModelMap model) {
        List<Attendance> attendances = attendanceService.listAttendances();
        model.addAttribute("attendances", attendances);
        return "user_to_provider_form";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/changeToProvider")
    public String changeToProvider(@RequestParam Integer attendanceId, @RequestParam Double pricePerHour,
                                   @RequestParam String description, @RequestParam MultipartFile image,
                                   @RequestParam String password, ModelMap model,
                                   HttpSession httpSession) {
        Person logged = (Person) httpSession.getAttribute("usersession");
        String name = logged.getName();
        String email = logged.getEmail();
        Long dni = logged.getDni();
        String lastName = logged.getLastName();
        String address = logged.getAddress();
        String phone = logged.getPhone();

        try {
            userService.setStatus(dni);
            providerService.create(name, email, password, dni, lastName, address, phone, description, pricePerHour,
                    attendanceId, image);
        } catch (Exception e) {
            List<Attendance> attendances = attendanceService.listAttendances();
            model.addAttribute("attendances", attendances);

            model.put("idAttendance", attendanceId);
            model.put("pricePerHour", pricePerHour);
            model.put("description", description);
            model.put("error", e.getMessage());

            return "user_to_provider_form";
        }
        return "redirect:/logout";
    }


    @GetMapping("/modify/{dni}")
    public String modify(HttpSession httpSession, ModelMap model){
        Person logged = (Person) httpSession.getAttribute("usersession");
        long dni = logged.getDni();
        model.addAttribute("user", userService.getOneById(dni));
        return "user_modify";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/modify/{dni}")
    public String modify(HttpSession httpSession, String name, String lastName, String phone, String address,
                         MultipartFile image, String password, ModelMap model){
        Person logged = (Person) httpSession.getAttribute("usersession");
        long dni = logged.getDni();
        try {
            userService.modify(dni, name, lastName, phone, address, image, password);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("user", providerService.getOne(dni));
            model.put("error", e.getMessage());
            return "user_modify";
        }
    }
}





