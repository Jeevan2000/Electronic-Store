package com.example.electronic.store.config;

import com.example.electronic.store.security.JWTAuthenicationFilter;
import com.example.electronic.store.security.JwtAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JWTAuthenicationFilter jwtAuthenicationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //configuration
        httpSecurity.authorizeHttpRequests(request ->
            request.requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/auth/**").authenticated()
                    .requestMatchers(HttpMethod.GET,"/products/**").permitAll()
                    .requestMatchers(HttpMethod.DELETE,"/users/").hasRole("ADMIN")
                    .anyRequest().permitAll()
        );

        //
        // httpSecurity.httpBasic(Customizer.withDefaults());

        httpSecurity.exceptionHandling(ex ->ex.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        httpSecurity.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtAuthenicationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder  passwordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationManagerBuilder) throws Exception {
        return authenticationManagerBuilder.getAuthenticationManager();
    }
}
