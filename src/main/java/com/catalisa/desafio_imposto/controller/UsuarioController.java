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

    public UsuarioController(UsuarioServiceImpl userServiceImpl,
                             AuthenticationManager authenticationManager,
                             JwtTokenProvider jwtTokenProvider,
                             UsuarioRepository usuarioRepository) {
        this.userServiceImpl = userServiceImpl;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> getUserInfo(@RequestBody CadastrarUsuarioDto cadastrarUsuarioDto) {

        if (usuarioRepository.findByUsername(cadastrarUsuarioDto.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body(Map.of("error", "Username já está em uso"));
        }

        Usuario savedUser = userServiceImpl.cadastraUsuario(cadastrarUsuarioDto);
        return ResponseEntity.status(201).body(Map.of(
                "id", savedUser.getId(),
                "username", savedUser.getUsername(),
                "role", savedUser.getRole()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {

        if (loginDto.getUsername() == null || loginDto.getUsername().isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("error", "O campo username é obrigatório"));
        }


        if (loginDto.getPassword() == null || loginDto.getPassword().isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("error", "O campo senha é obrigatório"));
        }

        try {

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
        } catch (Exception e) {

            return ResponseEntity.status(401).body(Map.of("error", "Credenciais inválidas"));
        }
    }
}
