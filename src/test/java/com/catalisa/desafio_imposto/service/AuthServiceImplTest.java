package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.LoginDto;
import com.catalisa.desafio_imposto.infra.jwt.JwtTokenProvider;
import com.catalisa.desafio_imposto.model.Usuario;
import com.catalisa.desafio_imposto.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Authentication authentication;


    @Test
    void login_Sucesso() {

        LoginDto loginDto = new LoginDto("testUser","testPassword");

        Usuario usuario = new Usuario();
        usuario.setUsername("testUser");

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        Mockito.when(usuarioRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(usuario));
        Mockito.when(jwtTokenProvider.generateToken(authentication))
                .thenReturn("mockedToken");


        String token = authServiceImpl.login(loginDto);

        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(usuarioRepository).findByUsername("testUser");
        Mockito.verify(jwtTokenProvider).generateToken(authentication);
        Assertions.assertEquals("mockedToken", token, "O token gerado deve ser igual ao esperado.");
    }

    @Test
    void login_usuarioNaoEncontrado() {

        LoginDto loginDto = new LoginDto("nonExistentUser","testPassword");


        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        Mockito.when(usuarioRepository.findByUsername("nonExistentUser"))
                .thenReturn(Optional.empty());


        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            authServiceImpl.login(loginDto);
        });

        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(usuarioRepository).findByUsername("nonExistentUser");
        Assertions.assertEquals("Usuário não encontrado", exception.getMessage(), "A mensagem de erro deve ser 'Usuário não encontrado'.");
    }

    @Test
    void login_ErroDeAutenticacao() {

        LoginDto loginDto = new LoginDto("testUser", "wrongPassword");
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Erro ao autenticar"));

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            authServiceImpl.login(loginDto);
        });

        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Assertions.assertEquals("Erro ao autenticar", exception.getMessage(), "A mensagem de erro deve ser 'Erro ao autenticar'.");
    }
}