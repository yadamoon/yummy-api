package com.bytepulse.yummy.security;

import org.springframework.beans.factory.annotation.Autowired;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// public class SecurityConfig {

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         // Configurable parameters for Argon2
//         int saltLength = 16; // 16 bytes
//         int hashLength = 32; // 32 bytes
//         int parallelism = 1; // Number of threads
//         int memory = 65536; // 64MB memory usage
//         int iterations = 3; // Iterations count

//         return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
//     }
// }

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bytepulse.yummy.config.auth.SecurityFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // User Creation
    // @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    // // InMemoryUserDetailsManager setup with two users
    // UserDetails admin = User.withUsername("Amiya")
    // .password(encoder.encode("123"))
    // .roles("ADMIN", "USER")
    // .build();

    // UserDetails user = User.withUsername("Ejaz")
    // .password(encoder.encode("123"))
    // .roles("USER")
    // .build();

    // return new InMemoryUserDetailsManager(admin, user);
    // }

    // // Configuring HttpSecurity
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception {
    // http
    // .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
    // .authorizeHttpRequests(auth -> auth
    // // .requestMatchers("/auth/welcome").permitAll() // Permit all access to
    // // /auth/welcome
    // .requestMatchers("/users").permitAll() // Permit all access to /auth/welcome

    // .requestMatchers("/auth/user/**").authenticated() // Require authentication
    // for /auth/user/**
    // .requestMatchers("/auth/admin/**").authenticated() // Require authentication
    // for /auth/admin/**
    // ); // Enable form-based login

    // return http.build();
    // }

    // // Password Encoding
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/books").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
