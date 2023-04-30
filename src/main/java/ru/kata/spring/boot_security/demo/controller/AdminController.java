package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersDetailsService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsersDetailsService userService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UsersDetailsService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/hello") //for test
    public String hello() {
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
    public String newUser(@ModelAttribute("users") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/createUsers";
        }
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
//            userValidator.validate(user, bindingResult);
//            if (bindingResult.hasErrors()) {
//                return "editUser";
//            }
        userService.updateUser(id, user);
        return "redirect:/admin";
    }
}
