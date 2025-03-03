package com.catalisa.desafio_imposto.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "impostos")
public class Imposto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double aliquota;
}
