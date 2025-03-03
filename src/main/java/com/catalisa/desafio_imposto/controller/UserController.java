package com.catalisa.desafio_imposto.controller;

import com.catalisa.desafio_imposto.dto.RegisterUserDto;
import com.catalisa.desafio_imposto.model.Usuario;
import com.catalisa.desafio_imposto.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserServiceImpl userServiceImpl;


    @PostMapping("/register")
    public ResponseEntity<?> getUserInfo(@RequestBody RegisterUserDto registerUserDto) {
        Usuario savedUser = userServiceImpl.registerUser(registerUserDto);

        // Retorna o ID e o username na resposta
        return ResponseEntity.status(201).body(Map.of(
                "id", savedUser.getId(),
                "username", savedUser.getUsername(),
                "role", savedUser.getRole()
        ));
    }
}
