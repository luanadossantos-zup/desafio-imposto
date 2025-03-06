package com.catalisa.desafio_imposto.controller;

import com.catalisa.desafio_imposto.dto.JwtResponse;
import com.catalisa.desafio_imposto.dto.LoginDto;
import com.catalisa.desafio_imposto.dto.CadastrarUsuarioDto;
import com.catalisa.desafio_imposto.infra.jwt.JwtTokenProvider;
import com.catalisa.desafio_imposto.model.Usuario;
import com.catalisa.desafio_imposto.repository.UsuarioRepository;
import com.catalisa.desafio_imposto.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl userServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/register")
    public ResponseEntity<?> getUserInfo(@RequestBody CadastrarUsuarioDto cadastrarUsuarioDto) {
        Usuario savedUser = userServiceImpl.cadastraUsuario(cadastrarUsuarioDto);

        // Retorna o ID e o username na resposta
        return ResponseEntity.status(201).body(Map.of(
                "id", savedUser.getId(),
                "username", savedUser.getUsername(),
                "role", savedUser.getRole()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );


        Usuario usuario = usuarioRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
