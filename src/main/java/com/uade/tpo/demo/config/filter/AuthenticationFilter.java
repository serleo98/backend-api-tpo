package com.uade.tpo.demo.config.filter;

import com.uade.tpo.demo.entity.dto.LoginPBDTO;
import com.uade.tpo.demo.repository.db.UserRepository;
import com.uade.tpo.demo.repository.rest.pocketbase.refreshToken.RefreshToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


//@Component
//@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    //@Autowired
    private RefreshToken refreshToken;
    //@Autowired
    private UserRepository userRepository;

    //@Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.isNotBlank(jwt) ) {

                Optional<LoginPBDTO> refreshValidation = refreshToken.execute(jwt);
                //refreshValidation
                //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
                //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
