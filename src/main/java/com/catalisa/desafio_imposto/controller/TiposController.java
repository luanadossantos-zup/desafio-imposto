package com.catalisa.desafio_imposto.controller;

import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.infra.jwt.JwtAuthenticationFilter;
import com.catalisa.desafio_imposto.model.Imposto;
import com.catalisa.desafio_imposto.model.Usuario;
import com.catalisa.desafio_imposto.service.ImpostoService;
import com.catalisa.desafio_imposto.service.ImpostoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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

    @GetMapping("/{id}")
    public ResponseEntity<Imposto>listarImpostoporId(@PathVariable Long id) {
        Optional<Imposto> imposto = impostoServiceImpl.buscarPorId(id);
        return imposto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
    public ResponseEntity<Void> deletarImposto(@PathVariable Long id) {
        impostoServiceImpl.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
