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
                           @RequestParam Double pricePerHour, ModelMap model) {
        try {
            providerServiceImpl.create(name, email, password, dni, lastName, address, phone, description, pricePerHour, idAttendance);
        } catch (Exception e) {
            List<Attendance> attendances = attendanceServiceImpl.listAttendances();
            model.addAttribute("attendances", attendances);
            model.put("fail", e.getMessage());
            return "provider_form";
        }
        return "redirect:/";
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
                         Integer idAttendance, ModelMap model) {
        try {
            providerServiceImpl.modifyProvider(dni, name, lastName, phone, email, address, password, role, description, pricePerHour, idAttendance);
            return "redirect:../list_providers";
        } catch (Exception e) {
            model.put("fail", e.getMessage());
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
            modelo.put("fail", e.getMessage());
        }
        return "provider_list";
    }

}
