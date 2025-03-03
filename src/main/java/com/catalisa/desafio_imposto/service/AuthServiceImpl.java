package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.LoginDto;
import com.catalisa.desafio_imposto.infra.jwt.JwtTokenProvider;
import com.catalisa.desafio_imposto.model.Usuario;
import com.catalisa.desafio_imposto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UsuarioRepository usuarioRepositoryRepository;
    
    @Override
    public String login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);


            Usuario usuario = usuarioRepositoryRepository
                    .findByUsername(loginDto.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));




            String token = jwtTokenProvider.generateToken(authentication);

            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao autenticar o usuário");
        }
    }
}
