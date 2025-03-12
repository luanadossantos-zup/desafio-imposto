package com.catalisa.desafio_imposto.dto;


public class CalculoImpostoRequest {
    private Long idImposto;
    private Double valorBase;

    public Long getIdImposto() {
        return idImposto;
    }



    public void setIdImposto(Long idImposto) {
        this.idImposto = idImposto;
    }

    public Double getValorBase() {
        return valorBase;
    }

    public void setValorBase(Double valorBase) {
        this.valorBase = valorBase;
    }


}
