package ru.kata.spring.boot_security.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})//
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    @NotEmpty(message = "Логин не может быть пустым")
    private String username;

    @NotEmpty(message = "email не может быть пустым")
    @Column(length = 50, nullable = false)
    private String email;

    @NotEmpty(message = "password не может быть пустым")
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;


    public User(String username, String email, String password, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Set<String> getRoleNames() {
//        Set<String> roleNames = new HashSet<>();
//        for (Role role : roles) {
//            String roleName = role.getAuthority().replaceAll("^\\[ROLE_(.*?)\\]$", "$1");
//            roleNames.add(roleName);
//        }
//        return roleNames;
//    }

    public Set<Role> getRoles() {
        return (Set<Role>) roles;
    }

    //("^\\[ROLE_(.*?)\\]", "")

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

