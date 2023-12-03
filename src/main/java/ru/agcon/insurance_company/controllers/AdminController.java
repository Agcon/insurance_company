package ru.agcon.insurance_company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.agcon.insurance_company.models.User;
import ru.agcon.insurance_company.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String userInfoPage(Model model){
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "users/users";
    }

    @PostMapping("/users/change/{id}")
    public String userChangeRole(@PathVariable("id") int id){
        User user = userService.getOne(id);
        if (user.getRole() == "USER") user.setRole("ADMIN");
        else user.setRole("USER");
        return "redirect:/admin/users";
    }

    @GetMapping("users/cart/{id}")
    public String userCartPage(@PathVariable("id") int id, Model model){
        User user = userService.getOne(id);
        model.addAttribute("insurances", user.getInsurances());
        return "users/user_cart";
    }

    @GetMapping("/users/{id}")
    public String showUserPage(@PathVariable("id") int id, Model model){
        User user = userService.getOne(id);
        model.addAttribute("user", user);
        return "users/user_page";
    }
}
