package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.CalculoImpostoRequest;
import com.catalisa.desafio_imposto.dto.CalculoImpostoResponse;
import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.model.Imposto;

import java.util.List;
import java.util.Optional;

public interface ImpostoService {

    List<ImpostoDto> listarTodosImpostos();

    Optional<Imposto> buscarPorId(Long id);

    Imposto salvarImposto (Imposto imposto);

    void deletar(Long id);

    CalculoImpostoResponse calcularImposto(CalculoImpostoRequest request);

    double calcularICMS(Double valorBase, Double aliquota);

    double calcularISS(Double valorBase, Double aliquota);

    double calcularIPI(Double valorBase, Double aliquota);
}
