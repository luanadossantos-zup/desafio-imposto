package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
