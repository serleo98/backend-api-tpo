package com.uade.tpo.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig {

    //private final JwtAuthenticationFilter jwtAuthFilter;
    //private final AuthenticationProvider authenticationProvider;
/**
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/error/**").permitAll()
                        //.requestMatchers("/categories/**").hasAnyAuthority(Role.USER.name())
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                //.authenticationProvider(authenticationProvider)
                //.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }*/

}
