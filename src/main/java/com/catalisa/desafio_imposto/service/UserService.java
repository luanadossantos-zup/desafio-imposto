package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.RegisterUserDto;
import com.catalisa.desafio_imposto.model.Usuario;

public interface UserService {
    public Usuario registerUser(RegisterUserDto registerUserDto);
}
