package com.catalisa.desafio_imposto.service;


import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.infra.jwt.JwtAuthenticationFilter;
import com.catalisa.desafio_imposto.model.Imposto;
import com.catalisa.desafio_imposto.model.TipoImposto;
import com.catalisa.desafio_imposto.repository.ImpostoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ImpostoServiceImpl implements ImpostoService{

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

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

    public Optional<Imposto> buscarPorId(Long id) {
        return impostoRepository.findById(id);
    }

    public Imposto salvarImposto (Imposto imposto) {
        return impostoRepository.save(imposto);
    }

    @Override
    public void deletar(Long id) {
        if (!impostoRepository.existsById(id)) {
            throw new EntityNotFoundException("Imposto com ID " + id + " não encontrado.");
        }

        log.info("Deletando imposto com id: {}", id);
        impostoRepository.deleteById(id);
        log.info("Imposto deletado com sucesso");
    }

    @Override
    public double calcular(TipoImposto tipoImposto, Double valorBase, Double aliquota) {
        switch (tipoImposto) {
            case ICMS:
                return calcularICMS(valorBase, aliquota);
            case ISS:
                return calcularISS(valorBase, aliquota);
            case IPI:
                return calcularIPI(valorBase, aliquota);
            default:
                throw new IllegalArgumentException("Tipo de imposto não suportado");
        }
    }

    private double calcularICMS(Double valorBase, Double aliquota) {
        return valorBase * (aliquota / 100);
    }

    private double calcularISS(Double valorBase, Double aliquota) {
        return valorBase * (aliquota / 100);
    }

    private double calcularIPI(Double valorBase, Double aliquota) {
        return valorBase * (aliquota / 100);
    }

}
