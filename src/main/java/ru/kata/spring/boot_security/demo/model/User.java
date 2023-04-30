package ru.kata.spring.boot_security.demo.model;


import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
//, uniqueConstraints = {@UniqueConstraint(columnNames = "username")}
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})//
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//unique = true не работает :(

    //@UniqueElements
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

    public User() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
//-------------------------------------------------------------------------------
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
//-------------------------------------------------------------------------------
    public User(Optional<User> byUsername) {}

    public User(String username, String email, String password, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
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

    public Set<Role> getRoles() {
        return (Set<Role>) roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


}

