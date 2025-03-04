package com.catalisa.desafio_imposto.controller;

import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.service.ImpostoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/tipos")
public class TipoController {

    private final ImpostoServiceImpl impostoServiceImpl;

    public TipoController(ImpostoServiceImpl impostoServiceImpl) {
        this.impostoServiceImpl = impostoServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<ImpostoDto>> listarTodosImpostos() {

        List<ImpostoDto> impostos = impostoServiceImpl.listarTodosImpostos();


        List<ImpostoDto> impostoDtos = impostos.stream()
                .map(imposto -> new ImpostoDto(
                        imposto.getId(),
                        imposto.getNomeImposto(),
                        imposto.getDescricao(),
                        imposto.getAliquota()
                ))
                .toList();


        return ResponseEntity.ok(impostoDtos);
    }


}
