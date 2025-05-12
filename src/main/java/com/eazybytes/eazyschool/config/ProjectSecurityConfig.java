package com.eazybytes.eazyschool.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/saveMsg")
                        .ignoringRequestMatchers(PathRequest.toH2Console())
                );

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/displayMessages").hasRole("ADMIN")
                        .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/holidays/**").authenticated()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/saveMsg").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/courses").permitAll()
                        .requestMatchers("/about").authenticated()
                        .requestMatchers("/assets/**").permitAll()

                )
                .formLogin(login -> login
                            .loginPage("/login")
                            .defaultSuccessUrl("/dashboard", true)  // Force redirect only on successful login
                            .failureUrl("/login?error=true")
                            .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll()
                )


                // Enables form login with default settings
                .httpBasic(httpBasic ->{})// Enables basic authentication

                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Updated syntax
                );
        return http.build();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails user= User.withDefaultPasswordEncoder().username("user").password("12345").roles("USER").build();
        UserDetails admin=User.withDefaultPasswordEncoder().username("admin").password("54321").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user,admin);
    }
}
