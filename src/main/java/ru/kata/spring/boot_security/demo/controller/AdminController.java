package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersDetailsService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsersDetailsService userService;

    @Autowired
    public AdminController(UsersDetailsService userService) {
        this.userService = userService;
    }

        @GetMapping("/all")
        public String startUsers(Model model) {
        model.addAttribute("user", userService.getUsers());
        return "allUsers";
    }

        @GetMapping()
        public String startAllUsers(Model model) {
        model.addAttribute("user", userService.getUsers());
        return "allUsers";
    }

    @GetMapping("/hello")
    public String hello () {
        return "SpringHello";
    }

//        @GetMapping("/{id}")
//        public String currentUser(@PathVariable("id") long id, Model model) {
//                model.addAttribute("user", userService.getUser());
//                return "userPage";
//        }
//
//
//
//        @PostMapping("/new")
//        public String createUser(@ModelAttribute("user") User user, @ModelAttribute("roles") List<Role> role) {
//                userService.(user);
//                return "redirect:/users/all";
//        }
//
//        @GetMapping("/new")
//        public String newUser(Model model) {
//            User user = new User();
//            model.addAttribute("user", user);
//            List<Role> roles = roleService.findAll();
//            model.addAttribute("allRoles", roles);
//                return "createUsers";
//        }
//
//        @DeleteMapping("/{id}")
//        public String deleteUser(@ModelAttribute("user") User user) {
//                userService.remove(user);
//                return "redirect:/users/all";
//        }
//
//        @GetMapping("/{id}/edit")
//        public String editUser(@PathVariable("id") long id, Model model) {
//                model.addAttribute("user", userService.getUser(id));
//                return "editUser";
//        }

//        @PatchMapping("/{id}")
//        public String editUser(@ModelAttribute("user") User user) {
//                userService.updateUser(user);
//                return "redirect:/users/all";
//        }

//        @GetMapping("/{id}")
//        public String currentUser(@PathVariable("id") long id, Model model) {
//                model.addAttribute("user", userService.getUser(id));
//                return "userPage";
//        }
}
