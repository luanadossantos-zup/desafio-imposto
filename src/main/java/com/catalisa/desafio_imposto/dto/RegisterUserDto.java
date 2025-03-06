package com.catalisa.desafio_imposto.dto;

import com.catalisa.desafio_imposto.model.Roles;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "username", "role", "id"})
public class RegisterUserDto {

    private String username;
    private String password;
    private Roles role;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Roles getRole() {
        return role;
    }


}
