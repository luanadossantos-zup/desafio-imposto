package com.catalisa.desafio_imposto.dto;

import com.catalisa.desafio_imposto.model.TipoImposto;

public class ImpostoInputDto {

    private TipoImposto nome;
    private String descricao;
    private Double aliquota;

    public ImpostoInputDto() {
    }

    public ImpostoInputDto(TipoImposto nome, String descricao, Double aliquota) {
        this.nome = nome;
        this.descricao = descricao;
        this.aliquota = aliquota;
    }

    public TipoImposto getNome() {
        return nome;
    }

    public void setNome(TipoImposto nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getAliquota() {
        return aliquota;
    }

    public void setAliquota(Double aliquota) {
        this.aliquota = aliquota;
    }
}
