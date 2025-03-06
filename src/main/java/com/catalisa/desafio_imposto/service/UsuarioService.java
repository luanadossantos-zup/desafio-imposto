package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.CadastrarUsuarioDto;
import com.catalisa.desafio_imposto.model.Usuario;

public interface UsuarioService {
    public Usuario cadastraUsuario(CadastrarUsuarioDto cadastrarUsuarioDto);
}
