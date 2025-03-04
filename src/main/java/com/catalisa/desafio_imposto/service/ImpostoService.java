package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.model.Imposto;
import com.catalisa.desafio_imposto.model.TipoImposto;

import java.util.List;
import java.util.Optional;

public interface ImpostoService {

    public List<ImpostoDto> listarTodosImpostos();

    public Optional<Imposto> buscarPorId(Long id);

    public void deletar(Long id);

    double calcular(TipoImposto tipoImposto, Double valorBase, Double aliquota);
}
