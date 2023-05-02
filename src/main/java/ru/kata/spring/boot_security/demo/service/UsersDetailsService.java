package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;


public interface UsersDetailsService {
    UserDetails loadUserByUsername(String username);
}
