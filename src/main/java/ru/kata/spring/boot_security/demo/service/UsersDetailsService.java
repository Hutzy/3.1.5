package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.security.UsersDetails;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class UsersDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
   // private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
    }


//    public void newUser(User user) {
//        userRepository.findByUsername(user.getUsername());
//        //user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//    }
//
//    public void remove(User user) {
//        userRepository.deleteById(user.getId());
//    }
//
//    public List<User> getUsers() {
//        return userRepository.findAll();
//    }
//
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
//
//    public void updateUser(Long id, User user) {
//        user.setId(id);
//        userRepository.save(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not Found");
        return new UsersDetails(user.get());
    }
}