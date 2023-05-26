package org.example.controller;

import org.example.entity.Contract;
import org.example.entity.Person;
import org.example.service.ContractServiceImpl;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    ContractServiceImpl contractService;

    @GetMapping("/profile")
    public String userMenu(HttpSession httpSession, ModelMap model) {
        Person logged = (Person) httpSession.getAttribute("usersession");
        System.out.println(logged.getName()+"prueba");
        if (logged!= null){
            long loggedDni = logged.getDni();
            model.addAttribute("logged",logged.getName());
            model.addAttribute("rol",logged.getRole());
            model.addAttribute("user",logged);
            model.addAttribute("userContracts", contractService.getAllContractsByUser(loggedDni));
        }else{
            return "login";
        }


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
                           @RequestParam String phone, @RequestParam MultipartFile image, ModelMap model){

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

        return "redirect:/";
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
    public String CancelContract(Integer contractId){
        try {
            contractService.statusChange(contractId,2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "user_menu";
    }





@GetMapping("/{contractId}/{buttonId}")
    public String buttonAction (@PathVariable Integer contractId, @PathVariable Integer buttonId, ModelMap model,HttpSession httpSession){

        // si el boton es 1, devuelve la vista para comentar + calificar, si es 2, es para cancelar el contrato
        Contract contract = contractService.getContractById(contractId);
        Person logged = (Person) httpSession.getAttribute("usersession");
        Long userId = logged.getDni();
        switch (buttonId){
            case 1:
                model.addAttribute("contract", contract);
                return "rate_provider";

            case 2: //Llamar al metodo para cancelar contrato?
                try {
                    contractService.statusChange(contractId, 4);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (logged!= null){
                    long loggedDni = logged.getDni();
                    model.addAttribute("logged",logged.getName());
                    model.addAttribute("rol",logged.getRole());
                    model.addAttribute("user",logged);
                    model.addAttribute("userContracts", contractService.getAllContractsByUser(loggedDni));
                }else{
                    return "login";
                }
                return "user_menu";
        }
        return "user_menu";
}

    }





