package com.catalisa.desafio_imposto.dto;

import com.catalisa.desafio_imposto.model.TipoImposto;


public class ImpostoDto {

    private Long id;

    private TipoImposto nome;

    private String descricao;

    private Double aliquota;

    public ImpostoDto() {
    }

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

    public TipoImposto getNome() {
        return nome;
    }


    public String getDescricao() {
        return descricao;
    }


    public Double getAliquota() {
        return aliquota;
    }

}
