package com.app.auth.config;

import com.app.auth.entity.User;
import com.app.auth.service.CustomUserDetailsService;
import com.app.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.app.auth.util.Constants.*;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            String authHeader = request.getHeader(AUTHORIZATION_HEADER);

            if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(BEARER)) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authHeader.substring(BEARER.length()).trim();
            if (StringUtils.isEmpty(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Empty token");
                return;
            }
            try {
                /*  Extracts, validates the JWT and throws JwtException if invalid  */
                Claims claims = jwtUtil.validateAndExtractClaims(token);

                // Validate token type - reject if not access token
                if(!claims.get(TOKEN_TYPE).equals(ACCESS_TOKEN)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token type");
                    return;
                }

                String email = claims.getSubject();
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User user = userDetailsService.loadUserByEmail(email);

                    /*
                     * UsernamePasswordAuthenticationToken is an implementation of Authentication
                     * that's designed for simple username/password authentication.
                     * For OAuth2: The OAuth2User and UserDetails interfaces are merged in Spring Security.
                     * The authenticated principal can be cast to either when needed.
                     */
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                filterChain.doFilter(request, response);
            } catch (JwtException ex) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Authentication failed");
        }
    }
}
