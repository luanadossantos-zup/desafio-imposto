package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.model.Roles;
import com.catalisa.desafio_imposto.model.Usuario;
import com.catalisa.desafio_imposto.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Test
    void carregarUsuarioPeloNome_Sucesso() {
        String username = "testUser";
        String password = "testPassword";
        Roles role = Roles.ROLE_USER;

        Usuario mockUsuario = new Usuario();
        mockUsuario.setUsername(username);
        mockUsuario.setPassword(password);
        mockUsuario.setRole(role);

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(mockUsuario));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());

        verify(usuarioRepository, times(1)).findByUsername(username);
    }

    @Test
    public void carregarUsuarioPeloNome_UsuarioNaoEncontrado() {

        String username = "nonExistentUser";
        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(username);
        });

        verify(usuarioRepository, times(1)).findByUsername(username);
    }
}