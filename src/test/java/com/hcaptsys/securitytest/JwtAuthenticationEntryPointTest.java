package com.hcaptsys.securitytest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.AuthenticationException;
import com.hcaptsys.security.JwtAuthenticationEntryPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import static org.mockito.Mockito.*;

public class JwtAuthenticationEntryPointTest {

    @Test
    public void testCommence_SuccessfulAuthentication() throws IOException, ServletException {
        // Mock HttpServletRequest and HttpServletResponse objects
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        // Mock AuthenticationException object
        AuthenticationException authException = Mockito.mock(AuthenticationException.class);

        // Create an instance of JwtAuthenticationEntryPoint
        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint();

        // Mock PrintWriter object
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // Call the commence() method with the mocked objects
        entryPoint.commence(request, response, authException);

        // Verify that the response status is set to SC_UNAUTHORIZED (401)
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Verify that the error message is printed in the response body
        verify(writer).println("Access Denied !! " + authException.getMessage());
    }

    @Test
    public void testCommence_UnauthorizedAccess() throws IOException, ServletException {
        // Mock HttpServletRequest and HttpServletResponse objects
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        // Mock AuthenticationException object
        AuthenticationException authException = Mockito.mock(AuthenticationException.class);

        // Create an instance of JwtAuthenticationEntryPoint
        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint();

        // Mock PrintWriter object
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // Call the commence() method with the mocked objects
        entryPoint.commence(request, response, authException);

        // Verify that the response status is set to SC_UNAUTHORIZED (401)
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Verify that the error message is printed in the response body
        verify(writer).println("Access Denied !! " + authException.getMessage());
    }

}