package org.example.controller;

import org.example.entity.Attendance;
import org.example.entity.Contract;
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

    @GetMapping("/modify_provider/{dni}")
    public String modify(@PathVariable Long dni, ModelMap model) {
        List<Contract> contracts = contractServiceImpl.getAllContracts();
        List<Attendance> attendances = attendanceServiceImpl.listAttendances();
        model.addAttribute("contracts", contracts);
        model.addAttribute("attendance", attendances);
        model.put("provider", providerServiceImpl.getOne(dni));
        return "provider_modify";
    }

    @PostMapping("/modify/{dni}")
    public String modify(Long dni, String name, String lastName, String phone, String email, String address,
                         String password, Role role, String description, Double pricePerHour,
                         Integer idAttendance, ModelMap model , @RequestParam MultipartFile image) {
        try {
            providerServiceImpl.modifyProvider(dni, name, lastName, phone, email, address, password, role, description, pricePerHour, idAttendance,image);
            return "redirect:../list_providers";
        } catch (Exception e) {
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

}