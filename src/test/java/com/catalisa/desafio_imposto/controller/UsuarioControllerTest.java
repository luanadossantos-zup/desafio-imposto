package com.catalisa.desafio_imposto.controller;

import com.catalisa.desafio_imposto.dto.CadastrarUsuarioDto;
import com.catalisa.desafio_imposto.dto.LoginDto;
import com.catalisa.desafio_imposto.infra.jwt.JwtTokenProvider;
import com.catalisa.desafio_imposto.model.Roles;
import com.catalisa.desafio_imposto.model.Usuario;
import com.catalisa.desafio_imposto.repository.UsuarioRepository;
import com.catalisa.desafio_imposto.service.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioServiceImpl userServiceImpl;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UsuarioRepository usuarioRepository;

    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        usuarioController = new UsuarioController(userServiceImpl, authenticationManager, jwtTokenProvider, usuarioRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void testRegister_UsernameAlreadyExists() throws Exception {
        CadastrarUsuarioDto cadastrarUsuarioDto = new CadastrarUsuarioDto();
        cadastrarUsuarioDto.setUsername("existinguser");
        cadastrarUsuarioDto.setPassword("password");
        cadastrarUsuarioDto.setRole(Roles.ROLE_USER);

        Mockito.when(usuarioRepository.findByUsername("existinguser"))
                .thenReturn(Optional.of(new Usuario()));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cadastrarUsuarioDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Username já está em uso"));

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByUsername("existinguser");
    }


    @Test
    void testLogin_UsernameNotProvided() throws Exception {
        LoginDto loginDto = new LoginDto(null, "password");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("O campo username é obrigatório"));
    }

    @Test
    void testLogin_PasswordNotProvided() throws Exception {
        LoginDto loginDto = new LoginDto("testuser", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("O campo senha é obrigatório"));
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        LoginDto loginDto = new LoginDto("testuser", "wrongpassword");

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Credenciais inválidas"));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Credenciais inválidas"));

        Mockito.verify(authenticationManager, Mockito.times(1))
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
    }

}