package org.example.controller;

import org.example.entity.*;
import org.example.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final ProviderService providerService;
    private final ContractService contractService;
    private final AttendanceService attendanceService;
    private final AdminService adminService;

    public AdminController(UserService userService, ProviderService providerService, ContractService contractService,
                           AttendanceService attendanceService, AdminService adminService) {
        this.userService = userService;
        this.providerService = providerService;
        this.contractService = contractService;
        this.attendanceService = attendanceService;
        this.adminService = adminService;
    }

    @GetMapping("/accounts")
    public String panel(ModelMap model) {
        List<User> users = userService.getUsers();
        List<Provider> providers = providerService.providers();
        List<Person> people = new ArrayList<>();
        people.addAll(users);
        people.addAll(providers);

        model.addAttribute("people", people);
        model.addAttribute("users", users);
        model.addAttribute("providers", providers);

        return "admin_users";
    }

    @PostMapping("/accounts")
    public String changeRole(Long accountId, ModelMap model) {
        List<User> users = userService.getUsers();
        List<Provider> providers = providerService.providers();
        List<Person> people = new ArrayList<>();
        people.addAll(users);
        people.addAll(providers);

        model.addAttribute("people", people);
        model.addAttribute("users", users);
        model.addAttribute("providers", providers);

        adminService.changeToAdmin(accountId);

        return "admin_users";
    }

    @GetMapping("/comments")
    public String commentariesPanel(ModelMap model) {
        List<Contract> contracts = contractService.getAllContracts();
        model.addAttribute("contracts", contracts);

        return "admin_comments";
    }

    @PostMapping("/comments")
    public String censure(@RequestParam Integer contractId, ModelMap model) {
        contractService.censureComment(contractId);
        List<Contract> contracts = contractService.getAllContracts();
        model.addAttribute("contracts", contracts);

        return "admin_comments";
    }

    @GetMapping("/attendances")
    public String attendancesPanel(ModelMap model) {
        List<Attendance> attendances = attendanceService.listAttendances();
        model.addAttribute("attendances", attendances);

        return "admin_attendances";
    }

    @PostMapping("/attendances")
    public String createAttendance(@RequestParam String name, String icon, ModelMap model) {
        List<Attendance> attendances = attendanceService.listAttendances();
        model.addAttribute("attendances", attendances);

        try {
            attendanceService.create(name, icon);
        } catch (Exception e) {
            model.put("name", name);
            model.put("icon", icon);
            model.put("error", e.getMessage());

            return "admin_attendances";
        }
        return "redirect:/admin/attendances";
    }
}
