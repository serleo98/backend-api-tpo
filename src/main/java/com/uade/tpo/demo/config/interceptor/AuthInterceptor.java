package com.uade.tpo.demo.config.interceptor;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.LoginPBDTO;
import com.uade.tpo.demo.repository.db.UserRepository;
import com.uade.tpo.demo.repository.rest.pokcetbase.refresh.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RefreshToken refreshToken;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.isNotBlank(jwt) ) {

                Optional<LoginPBDTO> refreshValidation = refreshToken.execute(jwt);
                if(refreshValidation.isPresent()){
                    String userId = refreshValidation.get().getRecord().getId();
                    Optional<User> userdb = userRepository.findByIdentityId(userId);
                    if(userdb.isPresent()){

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userdb.get(), null, new ArrayList<>());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        return true;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
