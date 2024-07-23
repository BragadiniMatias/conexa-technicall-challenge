package com.conexa.technicalchallenge.security;

import com.conexa.technicalchallenge.utils.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_SUFFIX)) {
            chain.doFilter(request, response);
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(SecurityConstants.HEADER);
        if (token != null) {
            Claims claims = Jwts.parserBuilder().setSigningKey(SecurityConstants.KEY.getBytes())
                    .build()
                    .parseClaimsJws(token.replace(SecurityConstants.TOKEN_SUFFIX, ""))
                    .getBody();
            String user = claims.getSubject();
            List<String> authorities = (List<String>) claims.get("authorities");
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
            }

        }
        return null;
    }
}
