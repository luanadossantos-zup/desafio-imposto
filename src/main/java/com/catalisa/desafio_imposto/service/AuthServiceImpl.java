package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.LoginDto;
import com.catalisa.desafio_imposto.exceptions.AuthenticationException;
import com.catalisa.desafio_imposto.infra.jwt.JwtTokenProvider;
import com.catalisa.desafio_imposto.model.Usuario;
import com.catalisa.desafio_imposto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

            String token = jwtTokenProvider.generateToken(authentication);
            return token;
        } catch (UsernameNotFoundException e) {

            throw e;
        } catch (RuntimeException e) {

            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException("Erro ao autenticar o usuário");
        }
    }
}
