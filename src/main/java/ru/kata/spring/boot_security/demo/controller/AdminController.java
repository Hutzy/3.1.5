package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersDetailsService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsersDetailsService userService;

    @Autowired
    public AdminController(UsersDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello") //for test
    public String hello () {
        return "SpringHello";
    }


    @GetMapping("/all")
        public String startUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "allUsers";
    }

        @GetMapping()
        public String startAllUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
            return "allUsers";
        }


    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("users", userService.getUser(id));
        return "userAdminPage";
    }

    @PostMapping()//new
    public String newUser(@ModelAttribute("users") User user) {
        userService.newUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("users", new User());
        return "createUsers";
    }

    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

        @GetMapping("/{id}/edit")
        public String editUser(@PathVariable("id") Long id, Model model) {
                model.addAttribute("user", userService.getUser(id));
                return "editUser";
        }

        @PatchMapping("/{id}")
        public String editUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
                userService.updateUser(id, user);
                return "redirect:/admin";
        }
}
