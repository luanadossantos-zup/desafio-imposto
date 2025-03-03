package com.catalisa.desafio_imposto.repository;

import com.catalisa.desafio_imposto.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
