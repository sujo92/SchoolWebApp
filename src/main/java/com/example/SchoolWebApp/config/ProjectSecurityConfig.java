package com.example.SchoolWebApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    //permit all requests
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/saveMsg").ignoringRequestMatchers("/public/**").ignoringRequestMatchers("/api/**"))
             .authorizeHttpRequests((requests) -> requests
                     .requestMatchers("dashboard").authenticated()
                     .requestMatchers("/displayMessages/**").hasRole("ADMIN")
                     .requestMatchers("/admin/**").hasRole("ADMIN")
                     .requestMatchers("/api/**").authenticated()
                     .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                     .requestMatchers("/student/**").hasRole("STUDENT")
                     .requestMatchers("/displayProfile").authenticated()
                     .requestMatchers("/updateProfile").authenticated()
                     .requestMatchers("/","/home").permitAll()
                     .requestMatchers("/holidays/**").permitAll()
                     .requestMatchers("/contact").permitAll()
                     .requestMatchers("/saveMsg").permitAll()
                     .requestMatchers("/courses").permitAll()
                     .requestMatchers("/about").permitAll()
                     .requestMatchers("/assets/**").permitAll()
                     .requestMatchers("/login").permitAll()
                     .requestMatchers("/logout").permitAll()
                     .requestMatchers("/public/**").permitAll())
                .formLogin(loginConfigurer -> loginConfigurer.loginPage("/login")
                        .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll())
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true).permitAll())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
