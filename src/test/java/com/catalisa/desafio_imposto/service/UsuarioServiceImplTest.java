package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.RegisterUserDto;
import com.catalisa.desafio_imposto.model.Roles;
import com.catalisa.desafio_imposto.model.Usuario;
import com.catalisa.desafio_imposto.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioServiceImpl;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void cadastraUsuario() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setUsername("testUser");
        registerUserDto.setPassword("password123");
        registerUserDto.setRole(Roles.ROLE_USER);

        Usuario usuarioMock = new Usuario();
        usuarioMock.setUsername("testUser");
        usuarioMock.setPassword("encodedPassword");
        usuarioMock.setRole(Roles.ROLE_USER);

        when(usuarioRepository.existsByUsername("testUser")).thenReturn(false);
        when(bCryptPasswordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        // Act
        Usuario result = usuarioServiceImpl.cadastraUsuario(registerUserDto);

        // Assert
        assertEquals("testUser", result.getUsername(), "The username should match the input.");
        assertEquals("encodedPassword", result.getPassword(), "The password should be encoded.");
        assertEquals(Roles.ROLE_USER, result.getRole(), "The role should match the input.");

        verify(usuarioRepository, times(1)).existsByUsername("testUser");
        verify(bCryptPasswordEncoder, times(1)).encode("password123");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }



}