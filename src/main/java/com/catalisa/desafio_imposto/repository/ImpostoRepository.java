package com.catalisa.desafio_imposto.repository;

import com.catalisa.desafio_imposto.model.Imposto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpostoRepository extends JpaRepository<Imposto, Long> {
}
