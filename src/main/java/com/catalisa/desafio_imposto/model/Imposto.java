package com.catalisa.desafio_imposto.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "impostos")
public class Imposto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private TipoImposto nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @Min(value = 0, message = "A alíquota não deve estar em branco!")
    @Column(nullable = false)
    private Double aliquota;

    public Imposto() {

    }

    public Imposto(Long id, TipoImposto nome, String descricao, Double aliquota) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.aliquota = aliquota;
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
