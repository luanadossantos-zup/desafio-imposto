package com.catalisa.desafio_imposto.dto;

import com.catalisa.desafio_imposto.model.TipoImposto;


public class ImpostoDto {

    private Long id;

    private TipoImposto nome;

    private String descricao;

    private Double aliquota;

    public ImpostoDto(Long id, TipoImposto nome, String descricao, Double aliquota) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.aliquota = aliquota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoImposto getNomeImposto() {
        return nome;
    }

    public void setNomeImposto(TipoImposto nome) {
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
