package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.model.TipoImposto;

import java.util.List;

public interface ImpostoService {

    public List<ImpostoDto> listarTodosImpostos();

    double calcular(TipoImposto tipoImposto, Double valorBase, Double aliquota);
}
