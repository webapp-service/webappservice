package org.example.controller;

import org.example.entity.Attendance;
import org.example.entity.Contract;
import org.example.entity.Provider;
import org.example.service.AttendanceService;
import org.example.service.ContractService;
import org.example.service.ProviderService;
import org.example.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/provider")
public class ControllerProvider {
    @Autowired
    ContractService contractService;
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    ProviderService providerService;

    @GetMapping("/register_provider")
    public String register(ModelMap modelo) {
        List<Contract> contracts = contractService.getAllContracts();
        List<Attendance> attendances = attendanceService.listAttendances();
        modelo.addAttribute("contracts", contracts);
        modelo.addAttribute("attendance", attendances);
        return "provider_form.html";
    }

    @PostMapping("/register_provider")
    public String register(@RequestParam String name, @RequestParam String email, @RequestParam String password,
                           @RequestParam Long dni, @RequestParam String lastName, @RequestParam String addres,
                           @RequestParam Long phone, @RequestParam MultipartFile image, @RequestParam String description,
                           @RequestParam Double pricePerHour, @RequestParam Integer idAttendance, @RequestParam Integer idContract, ModelMap model) throws Exception {
        try {
            providerService.create(name, email, password, dni, lastName, addres, phone, image, description, pricePerHour, idAttendance, idContract);
            model.put("success", "The provider was successfully loaded");
        } catch (Exception e) {
            List<Contract> contracts = contractService.getAllContracts();
            List<Attendance> attendances = attendanceService.listAttendances();
            model.addAttribute("contracts", contracts);
            model.addAttribute("attendance", attendances);
            model.put("fail", e.getMessage());
            return "provider_form.html";
        }
        return "index.html";
    }

    @GetMapping("/modify_provider/{dni}")
    public String modify(@PathVariable Long dni, ModelMap model) {
        List<Contract> contracts = contractService.getAllContracts();
        List<Attendance> attendances = attendanceService.listAttendances();
        model.addAttribute("contracts", contracts);
        model.addAttribute("attendance", attendances);
        model.put("provider", providerService.getOne(dni));
        return "provider_modify.html";
    }

    @PostMapping("/modify/{dni}")
    public String modify(Long dni, String name, String lastName, Long phone, String email, String address,
                         MultipartFile image, String password, Role role, String description, Double pricePerHour,
                         Integer idAttendance, Integer idContract, ModelMap model) {
        try {
            providerService.modifyProvider(dni, name, lastName, phone, email, address, image, password, role, description, pricePerHour, idAttendance, idContract);
            return "redirect:../list_providers";
        } catch (Exception e) {
            model.put("fail", e.getMessage());
            return "provider_modify.html";
        }
    }

    @GetMapping("/list_providers")
    public String list(ModelMap model) {
        List<Provider> providers = providerService.providers();
        model.addAttribute("providers", providers);
        return "provider_list.html";
    }

    @GetMapping("/delete/{dni}")
    public String delete(@PathVariable Long dni, ModelMap modelo) {
        try {
            providerService.delete(dni);
            modelo.put("success", "The provider was deleted successfully");
        } catch (Exception e) {
            modelo.put("fail", e.getMessage());
        }
        return "provider_list.html";
    }

}
