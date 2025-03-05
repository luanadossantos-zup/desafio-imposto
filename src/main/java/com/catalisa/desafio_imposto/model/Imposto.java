package com.catalisa.desafio_imposto.model;


import jakarta.persistence.*;

@Entity
@Table(name = "impostos")
public class Imposto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private TipoImposto nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double aliquota;

    public Imposto() {

    }

    public Long getId() {
        return id;
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
