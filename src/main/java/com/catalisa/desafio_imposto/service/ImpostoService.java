package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.CalculoImpostoRequest;
import com.catalisa.desafio_imposto.dto.CalculoImpostoResponse;
import com.catalisa.desafio_imposto.dto.ImpostoDto;

import java.util.List;

public interface ImpostoService {

    public List<ImpostoDto> listarTodosImpostos();

    public CalculoImpostoResponse calcularImposto(CalculoImpostoRequest request);
}
