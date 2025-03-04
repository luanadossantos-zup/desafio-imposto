package com.catalisa.desafio_imposto.model;

public enum TipoImposto implements CalculoImposto{
    ICMS {
        @Override
        public double calcular(Double valorBase, Double aliquota) {
            return valorBase * (aliquota / 100);
        }
    },
    ISS {
        @Override
        public double calcular(Double valorBase, Double aliquota) {
            return valorBase * (aliquota / 100);
        }
    },
    IPI {
        @Override
        public double calcular(Double valorBase, Double aliquota) {
            return valorBase * (aliquota / 100);
        }
    };

}
