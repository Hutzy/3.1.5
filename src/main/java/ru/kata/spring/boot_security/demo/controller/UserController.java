package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.transaction.Transactional;


@Controller
@RequestMapping("/users")
public class UserController {

        private final UserService userService;

        @Autowired
        public UserController(UserService userService) {
                this.userService = userService;
        }

        @GetMapping("/all")
        public String startUsers(Model model) {
                model.addAttribute("user", userService.getUsers());
                return "allUsers";
        }

        @PostMapping()//new
        public String createUser(@ModelAttribute("user") User user) {
                userService.add(user);
                return "redirect:/users/all";
        }

        @GetMapping("/new")
        public String newUser(Model model) {
                model.addAttribute("user", new User());
                return "createUsers";
        }

        @DeleteMapping("/{id}")
        public String deleteUser(@ModelAttribute("user") User user) {
                userService.remove(user);
                return "redirect:/users/all";
        }

        @GetMapping("/{id}/edit")
        public String editUser(@PathVariable("id") long id, Model model) {
                model.addAttribute("user", userService.getUser(id));
                return "editUser";
        }

        @PatchMapping("/{id}")
        public String editUser(@ModelAttribute("user") User user) {
                userService.updateUser(user);
                return "redirect:/users/all";
        }

        @GetMapping("/{id}")
        public String currentUser(@PathVariable("id") long id, Model model) {
                model.addAttribute("user", userService.getUser(id));
                return "userPage";
        }
}