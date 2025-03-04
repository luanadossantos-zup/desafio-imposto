package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.CalculoImpostoRequest;
import com.catalisa.desafio_imposto.dto.CalculoImpostoResponse;
import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.model.Imposto;
import com.catalisa.desafio_imposto.repository.ImpostoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ImpostoServiceImpl implements ImpostoService{

    @Autowired
    private ImpostoRepository impostoRepository;


    public List<ImpostoDto> listarTodosImpostos() {
        List<ImpostoDto> impostos = impostoRepository
                .findAll()
                .stream()
                .map(entity -> new ImpostoDto(entity.getId(),entity.getNome(),entity.getDescricao(), entity.getAliquota()))
                .collect(Collectors.toList());
        impostos.forEach(System.out::println);
        return impostos;
    }

    public Imposto salvarImposto (Imposto imposto) {
        return impostoRepository.save(imposto);
    }


    private double calcularICMS(Double valorBase, Double aliquota) {
        return valorBase * (aliquota / 100);
    }

    private double calcularISS(Double valorBase, Double aliquota) {
        return valorBase * (aliquota / 100);
    }

        CalculoImpostoResponse response = new CalculoImpostoResponse();
        response.setTipoImposto(imposto.getNome().name());
        response.setValorBase(request.getValorBase());
        response.setAliquota(imposto.getAliquota());
        response.setValorImposto(valorImposto);

        return response;
    }

}
