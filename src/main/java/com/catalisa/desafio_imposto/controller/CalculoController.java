package com.catalisa.desafio_imposto.controller;

import com.catalisa.desafio_imposto.dto.CalculoImpostoRequest;
import com.catalisa.desafio_imposto.dto.CalculoImpostoResponse;
import com.catalisa.desafio_imposto.service.ImpostoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculo")
public class CalculoController {

    @Autowired
    private ImpostoServiceImpl impostoServiceImpl;


    @PostMapping
    public ResponseEntity<CalculoImpostoResponse> calcularImposto(@RequestBody CalculoImpostoRequest request) {
        try {
            CalculoImpostoResponse response = impostoServiceImpl.calcularImposto(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
