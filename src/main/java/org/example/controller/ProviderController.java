package org.example.controller;

import org.example.entity.Attendance;
import org.example.entity.Contract;
import org.example.entity.Person;
import org.example.entity.Provider;
import org.example.service.AttendanceServiceImpl;
import org.example.service.ContractServiceImpl;
import org.example.service.ProviderServiceImpl;
import org.example.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    ContractServiceImpl contractServiceImpl;
    @Autowired
    AttendanceServiceImpl attendanceServiceImpl;
    @Autowired
    ProviderServiceImpl providerServiceImpl;

    private Provider provider;

    @GetMapping("/register")
    public String register(ModelMap modelo) {
        List<Attendance> attendances = attendanceServiceImpl.listAttendances();
        modelo.addAttribute("attendances", attendances);

            return "provider_form";

    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email, @RequestParam String password,
                           @RequestParam Long dni, @RequestParam String lastName, @RequestParam String address,
                           @RequestParam String phone, @RequestParam String description, @RequestParam Integer idAttendance,
                           @RequestParam Double pricePerHour, ModelMap model ,@RequestParam MultipartFile image) {
        try {
            providerServiceImpl.create(name, email, password, dni, lastName, address, phone, description, pricePerHour, idAttendance,image);
        } catch (Exception e) {
            List<Attendance> attendances = attendanceServiceImpl.listAttendances();
            model.addAttribute("attendances", attendances);

            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("dni", dni);
            model.addAttribute("lastName", lastName);
            model.addAttribute("address", address);
            model.addAttribute("phone", phone);
            model.addAttribute("description", description);
            model.addAttribute("pricePerHour", pricePerHour);
            model.addAttribute("image", image);

            provider=new Provider();
            provider.setDni(dni);
            provider.setName(name);
            provider.setLastName(lastName);
            provider.setPhone(phone);
            provider.setEmail(email);
            provider.setAddress(address);
            provider.setPassword(password);
            provider.setDescription(description);
            provider.setPricePerHour(pricePerHour);

            model.put("error", e.getMessage());

            return "provider_form";
        }
        return "redirect:/login";
    }

    @GetMapping("/modify/{dni}")
    public String modify(HttpSession httpSession, ModelMap model) {
        Person logged = (Person) httpSession.getAttribute("usersession");
        long dni = logged.getDni();
        List<Attendance> attendances = attendanceServiceImpl.listAttendances();
        model.addAttribute("attendances", attendances);
        model.addAttribute("provider", providerServiceImpl.getOne(dni));
        return "provider_modify";
    }

    @PostMapping("/modify/{dni}")
    public String modify(HttpSession httpSession, String name, String lastName, String phone, String address,
                         String password, String description, Double pricePerHour,
                         Integer idAttendance, ModelMap model , @RequestParam MultipartFile image) {
        Person logged = (Person) httpSession.getAttribute("usersession");
        long dni = logged.getDni();
        try {
            providerServiceImpl.modifyProvider(dni, name, lastName, phone, address, password, description, pricePerHour, idAttendance,image);
            return "redirect:/";
        } catch (Exception e) {
            List<Attendance> attendances = attendanceServiceImpl.listAttendances();
            model.addAttribute("attendances", attendances);
            model.addAttribute("provider", providerServiceImpl.getOne(dni));
            model.put("error", e.getMessage());
            return "provider_modify";
        }
    }

    @GetMapping("/list_providers")
    public String list(ModelMap model) {
        List<Provider> providers = providerServiceImpl.providers();
        model.addAttribute("providers", providers);
        return "provider_list";
    }

    @GetMapping("/delete/{dni}")
    public String delete(@PathVariable Long dni, ModelMap modelo) {
        try {
            providerServiceImpl.delete(dni);
            modelo.put("success", "The provider was deleted successfully");
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "provider_list";
    }

    @GetMapping("/{contractId}/{buttonId}")
    public String buttonAction(@PathVariable Integer contractId, @PathVariable Integer buttonId, ModelMap model, HttpSession httpSession) {

        Contract contract = contractServiceImpl.getContractById(contractId);
        Person logged = (Person) httpSession.getAttribute("usersession");
        Long userId = logged.getDni();
        switch (buttonId) {
            case 1:
                try {
                    contractServiceImpl.statusChange(contractId, 2);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return "redirect:/provider/profile";
            case 2:
                try {
                    contractServiceImpl.statusChange(contractId, 4);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return "redirect:/provider/profile";
            /*case 3:
                try {
                    contractServiceImpl.statusChange(contractId, 4);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return "redirect:/provider/profile";

             */
        }
        return "redirect:/provider/profile";
    }

    @GetMapping("/profile")
    public String userMenu(HttpSession httpSession, ModelMap model) {
        Person logged = (Person) httpSession.getAttribute("usersession");
        if (logged != null){
            long loggedDni = logged.getDni();
            model.addAttribute("logged",logged.getName());
            model.addAttribute("rol",logged.getRole());
            model.addAttribute("provider",logged);
            model.addAttribute("providerContracts", contractServiceImpl.createContractDTO(loggedDni));
        }else{
            return "login";
        }
        return "provider_menu";
    }

}