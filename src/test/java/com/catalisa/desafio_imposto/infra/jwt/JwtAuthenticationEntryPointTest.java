package com.catalisa.desafio_imposto.infra.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;

import org.springframework.security.core.AuthenticationException;
import java.io.IOException;

import static org.mockito.Mockito.*;


class JwtAuthenticationEntryPointTest {
    @Test
    void testCommence() throws IOException, ServletException {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AuthenticationException mockAuthException = mock(AuthenticationException.class);

        when(mockAuthException.getMessage()).thenReturn("Unauthorized");

        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint();

        entryPoint.commence(mockRequest, mockResponse, mockAuthException);

        verify(mockResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}