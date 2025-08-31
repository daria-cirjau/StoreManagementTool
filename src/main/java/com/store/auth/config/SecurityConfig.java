package com.store.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/grantRole").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/auth/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/auth/users/*/roles").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/products/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categories/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/categories/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/price/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/price/**").hasAuthority("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
