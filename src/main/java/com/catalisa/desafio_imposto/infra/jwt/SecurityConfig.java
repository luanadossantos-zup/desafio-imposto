package com.catalisa.desafio_imposto.infra.jwt;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
@Component
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/user/register").permitAll();
                    authorize.requestMatchers("/user/login").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/tipos").authenticated();
                    authorize.requestMatchers(HttpMethod.POST, "/tipos").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.GET, "/tipos/**").authenticated();
                    authorize.requestMatchers(HttpMethod.DELETE, "/tipos/**").hasRole("ADMIN");
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());

        http.exceptionHandling( exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint));

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
