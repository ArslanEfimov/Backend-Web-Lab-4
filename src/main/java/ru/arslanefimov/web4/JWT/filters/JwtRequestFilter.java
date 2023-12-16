package ru.arslanefimov.web4.JWT.filters;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.arslanefimov.web4.JWT.JwtTokenUtils;

import java.io.IOException;
import java.security.SignatureException;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private  JwtTokenUtils jwtTokenUtils;



    //Проверяем токен и записываем его в контекст
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = null;

        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if ("Token".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        String userName  = null;

        System.out.println(jwtToken);
        if(jwtToken!=null){
            try {
                userName = jwtTokenUtils.getUserName(jwtToken);
            }catch (ExpiredJwtException ex) {
                log.debug("Token lifetime has expired");
            }

            if(userName!=null && SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userName,
                        null,
                        jwtTokenUtils.getRoles(jwtToken).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }


        }
        filterChain.doFilter(request, response);
    }
}
