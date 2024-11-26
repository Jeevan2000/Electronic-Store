package com.example.electronic.store.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "users")
public class User  implements UserDetails {



    @Id
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_password", length = 200)
    private String password;

    @Column(name ="user_gender")
    private String gender;

    @Column(name = "user_about", length = 100)
    private String about;

    @Column(name = "user_image_name")
    private String imageName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorithies = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
        return authorithies;
    }

    @Override
    public String getUsername() {
        return this.email;
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

}
