package com.hcaptsys.securitytest;

import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.hcaptsys.security.JwtAuthenticationFilter;
import com.hcaptsys.security.JwtHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilterTest {

    @Mock
    private JwtHelper jwtHelper;

    @Mock
    private UserDetailsService userDetailsService;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.jwtHelper = jwtHelper;
        jwtAuthenticationFilter.userDetailsService = userDetailsService;
    }

    @Test
    public void testDoFilterInternal_ValidToken_Success() throws ServletException {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJydXNoaXJybTIxQGdtYWlsLmNvbSIsImlhdCI6MTY5NDQwODA5NSwiZXhwIjoxNjk0NDI2MDk1fQ.QPggxnZelBYJgj40hjSblckvdQ2RQJhXMT4gw8DbDBIinMNNb0WXCt9-Ks8i0nozUu5n7V0UpFno408d2G7KlA";
        String username = "rushirrm21@gmail.com";

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtHelper.getUsernameFromToken(token)).thenReturn(username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User(username, "", authorities);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtHelper.validateToken(token, userDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        assert authentication instanceof UsernamePasswordAuthenticationToken;
        assert authentication.getPrincipal().equals(userDetails);
        assert authentication.getAuthorities().equals(authorities);
    }

    @Test
    public void testDoFilterInternal_InvalidToken_Exception() throws ServletException {
        String token = "invalid_token";

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtHelper.getUsernameFromToken(token)).thenThrow(MalformedJwtException.class);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication == null;
    }

    @Test
    public void testDoFilterInternal_ExpiredToken_Exception() throws ServletException {
        String token = "expired_token";

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtHelper.getUsernameFromToken(token)).thenThrow(ExpiredJwtException.class);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication == null;
    }

    @Test
    public void testDoFilterInternal_InvalidHeader_Exception() throws ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Invalid Header");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication == null;
    }
}
