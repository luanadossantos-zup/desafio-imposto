package com.catalisa.desafio_imposto.service;


import com.catalisa.desafio_imposto.dto.CalculoImpostoRequest;
import com.catalisa.desafio_imposto.dto.CalculoImpostoResponse;
import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.infra.jwt.JwtAuthenticationFilter;
import com.catalisa.desafio_imposto.model.Imposto;
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
        validarImposto(imposto);
        return impostoRepository.save(imposto);
    }

    public void validarImposto(Imposto imposto) {
        if (imposto.getNome() == null || imposto.getNome().name().isEmpty()) {
            throw new IllegalArgumentException("O nome do imposto é obrigatório.");
        }

        if (imposto.getAliquota() == null || imposto.getAliquota() <= 0) {
            throw new IllegalArgumentException("A alíquota deve ser maior que zero.");
        }

        if (imposto.getDescricao() == null || imposto.getDescricao().length() > 100) {
            throw new IllegalArgumentException("A descrição é obrigatória e deve ter no máximo 100 caracteres.");
        }
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
    public CalculoImpostoResponse calcularImposto(CalculoImpostoRequest request) {

        Imposto imposto = impostoRepository.findById(request.getIdImposto())
                .orElseThrow(() -> new RuntimeException("Imposto não encontrado"));


        double valorImposto;
        switch (imposto.getNome()) {
            case ICMS:
                valorImposto = calcularICMS(request.getValorBase(), imposto.getAliquota());
                break;
            case ISS:
                valorImposto = calcularISS(request.getValorBase(), imposto.getAliquota());
                break;
            case IPI:
                valorImposto = calcularIPI(request.getValorBase(), imposto.getAliquota());
                break;
            default:
                throw new IllegalArgumentException("Tipo de imposto não suportado");
        }

        CalculoImpostoResponse response = new CalculoImpostoResponse();
        response.setTipoImposto(imposto.getNome().name());
        response.setValorBase(request.getValorBase());
        response.setAliquota(imposto.getAliquota());
        response.setValorImposto(valorImposto);

        return response;
    }

    //ICMS
    //Um produto custa R$ 1.000,00 reais e sobre ele incide a alíquota de 18%.
    //Neste caso o valor do ICMS deste produto seria de R$ 180,00, totalizando R$ 1.180,00.
    public double calcularICMS(Double valorBase, Double aliquota) {
        return valorBase * (aliquota / 100);
    }

    //ISS
    //Uma empresa de consultoria em Salvador prestando um serviço no valor de R$ 20.000.
    // Com uma alíquota de 2,5%, o valor do ISS será de R$ 500,00 (R$ 20.000 x 2,5%).
    public double calcularISS(Double valorBase, Double aliquota) {
        return valorBase * (aliquota / 100);
    }

    //IPI
    //Valor do IPI = Base de cálculo x (Alíquota / 100).
    public double calcularIPI(Double valorBase, Double aliquota) {
        return valorBase * (aliquota / 100);
    }

}
