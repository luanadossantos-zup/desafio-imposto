package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.CalculoImpostoRequest;
import com.catalisa.desafio_imposto.dto.CalculoImpostoResponse;
import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.model.Imposto;

import java.util.List;
import java.util.Optional;

public interface ImpostoService {

    public List<ImpostoDto> listarTodosImpostos();

    public Optional<Imposto> buscarPorId(Long id);

    public void deletar(Long id);

    public CalculoImpostoResponse calcularImposto(CalculoImpostoRequest request);
}
