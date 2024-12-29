package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String showUsersTable(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin";
    }

    @GetMapping(value = "/new")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUserForm";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("roles") Set<Role> roles) {
        userService.saveUser(userService.createUser(user, roles));
        return "redirect:/admin/";
    }

    @GetMapping(value = "/edit")
    public String edit(Model model, @RequestParam(value = "id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "updateuser";
    }

    @PostMapping(value = "/edit")
    public String update(@RequestParam("id") Long id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
