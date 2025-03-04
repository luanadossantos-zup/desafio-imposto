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
                        imposto.getNome(),
                        imposto.getDescricao(),
                        imposto.getAliquota()
                ))
                .toList();


        return ResponseEntity.ok(impostoDtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ImpostoDto> cadastrarNovoImposto(@RequestBody ImpostoDto impostoDto) {

        Imposto imposto = new Imposto();
        imposto.setNome(impostoDto.getNome());
        imposto.setDescricao(impostoDto.getDescricao());
        imposto.setAliquota(impostoDto.getAliquota());

        Imposto impostoSalvo = impostoServiceImpl.salvarImposto(imposto);

        ImpostoDto impostoSalvoDto = new ImpostoDto(
                impostoSalvo.getId(),
                impostoSalvo.getNome(),
                impostoSalvo.getDescricao(),
                impostoSalvo.getAliquota()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(impostoSalvoDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        impostoServiceImpl.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
