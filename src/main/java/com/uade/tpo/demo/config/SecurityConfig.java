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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(corsConfigurer -> corsConfigurationSource()).csrf().disable()
                .authorizeHttpRequests(
                        req -> req

                                //USER
                                .requestMatchers("/user/login").permitAll()
                                .requestMatchers("/user/sign-in").permitAll()
                                .requestMatchers("/user/refresh").hasAuthority("USER")
                                .requestMatchers("/discount").hasAuthority("USER")

                                .requestMatchers("/imagen/**").permitAll()
                                //PRODUCTS
                                .requestMatchers(HttpMethod.POST,"/products/**").hasAuthority("USER")
                                .requestMatchers(HttpMethod.PUT,"/products/**").hasAuthority("USER")
                                .requestMatchers(HttpMethod.GET,"/products/**").permitAll()

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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
