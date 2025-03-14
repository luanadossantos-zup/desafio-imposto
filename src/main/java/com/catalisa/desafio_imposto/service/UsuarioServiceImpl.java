package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.CadastrarUsuarioDto;
import com.catalisa.desafio_imposto.model.Usuario;
import com.catalisa.desafio_imposto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Usuario cadastraUsuario(CadastrarUsuarioDto cadastrarUsuarioDto) {

        validaExistenciaDoUsuario(cadastrarUsuarioDto);

        Usuario usuario = new Usuario();
        usuario.setUsername(cadastrarUsuarioDto.getUsername());
        usuario.setPassword(bCryptPasswordEncoder.encode(cadastrarUsuarioDto.getPassword()));
        usuario.setRole(cadastrarUsuarioDto.getRole());

        return usuarioRepository.save(usuario);
    }

    private void validaExistenciaDoUsuario(CadastrarUsuarioDto cadastrarUsuarioDto) {
        if (usuarioRepository.existsByUsername(cadastrarUsuarioDto.getUsername())) {
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }
    }
}
