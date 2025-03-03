package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.RegisterUserDto;
import com.catalisa.desafio_imposto.model.Usuario;
import com.catalisa.desafio_imposto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Usuario registerUser(RegisterUserDto registerUserDto) {
        if (usuarioRepository.existsByUsername(registerUserDto.getUsername())) {
            throw new RuntimeException("Unprocess Entity");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(registerUserDto.getUsername());
        usuario.setPassword(bCryptPasswordEncoder.encode(registerUserDto.getPassword()));
        usuario.setRole(registerUserDto.getRole());

        // Salva o usuário e retorna o objeto salvo
        return usuarioRepository.save(usuario);
    }
}
