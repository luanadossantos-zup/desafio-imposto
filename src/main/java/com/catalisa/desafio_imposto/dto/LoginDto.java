package com.catalisa.desafio_imposto.dto;

public class LoginDto {
    private String username;
    private String password;

    public LoginDto() {
    }


    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


}
