package com.catalisa.desafio_imposto.infra.jwt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private Authentication authentication;

    @Test
    void generateToken_DeveGerarTokenValido() {
        String username = "testUser";
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        Mockito.when(authentication.getName()).thenReturn(username);
        Mockito.when(authentication.getAuthorities()).thenAnswer(invocation -> authorities);

        String token = jwtTokenProvider.generateToken(authentication);

        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals(username, jwtTokenProvider.getUsername(token));
    }

    @Test
    void getUsername_DeveRetornarUsernameCorreto() {
        String username = "testUser";
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        Mockito.when(authentication.getName()).thenReturn(username);
        Mockito.when(authentication.getAuthorities()).thenAnswer(invocation -> authorities);

        String token = jwtTokenProvider.generateToken(authentication);

        String extractedUsername = jwtTokenProvider.getUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void validateToken_DeveRetornarTrueParaTokenValido() {
        String username = "testUser";
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        Mockito.when(authentication.getName()).thenReturn(username);
        Mockito.when(authentication.getAuthorities()).thenAnswer(invocation -> authorities);

        String token = jwtTokenProvider.generateToken(authentication);

        boolean isValid = jwtTokenProvider.validateToken(token);

        assertTrue(isValid);
    }



}