package com.uade.tpo.demo.config;

import com.uade.tpo.demo.config.filter.AuthenticationFilter;
import com.uade.tpo.demo.config.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic().disable()
                .cors().and().csrf().disable()
                .authorizeHttpRequests(
                        req -> req

                                //USER
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/sign-in").permitAll()
                                .requestMatchers("/refresh").hasAuthority("USER")

                                //PRODUCTS
                                .requestMatchers(HttpMethod.POST,"/products/**").hasAuthority("USER")
                                .requestMatchers(HttpMethod.PUT,"/products/**").hasAuthority("USER")
                                .requestMatchers(HttpMethod.GET,"/products").permitAll()

                                .requestMatchers("/transactions/**").hasAuthority("USER")

                                .requestMatchers("/payments/**").hasAuthority("USER")

                                //SWAGGER
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/ping").permitAll()
                                .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
