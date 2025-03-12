package com.catalisa.desafio_imposto.controller;

import com.catalisa.desafio_imposto.dto.CalculoImpostoRequest;
import com.catalisa.desafio_imposto.dto.CalculoImpostoResponse;
import com.catalisa.desafio_imposto.model.TipoImposto;
import com.catalisa.desafio_imposto.service.ImpostoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculoControllerTest {

    @InjectMocks
    private CalculoController calculoController;

    @Mock
    private ImpostoServiceImpl impostoServiceImpl;

    @Test
    public void testaCalculoImposto_RetornoValido () {

        CalculoImpostoRequest request = new CalculoImpostoRequest();
        request.setIdImposto(1L);
        request.setValorBase(1000.0);

        CalculoImpostoResponse responseMock = new CalculoImpostoResponse();
        responseMock.setValorImposto(100.0);

        Mockito.when(impostoServiceImpl.calcularImposto(request)).thenReturn(responseMock);

        // Act
        ResponseEntity<CalculoImpostoResponse> response = calculoController.calcularImposto(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMock, response.getBody());
        assertEquals(100.0, response.getBody().getValorImposto());

    }

    @Test
    public void testaCalculoImposto_RetornoInvalido() {
        CalculoImpostoRequest request = new CalculoImpostoRequest();
        request.setIdImposto(1L);
        request.setValorBase(1000.0);

        Mockito.when(impostoServiceImpl.calcularImposto(request)).thenThrow(new RuntimeException("Erro ao calcular imposto"));

        ResponseEntity<CalculoImpostoResponse> response = calculoController.calcularImposto(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

}