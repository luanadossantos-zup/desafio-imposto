package com.catalisa.desafio_imposto.controller;

import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.dto.ImpostoInputDto;
import com.catalisa.desafio_imposto.model.Imposto;
import com.catalisa.desafio_imposto.service.ImpostoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/tipos")
public class TiposController {
    private final ImpostoServiceImpl impostoServiceImpl;

    public TiposController(ImpostoServiceImpl impostoServiceImpl) {
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
    public ResponseEntity<Imposto> listarImpostoporId(@PathVariable Long id) {
        Optional<Imposto> imposto = impostoServiceImpl.buscarPorId(id);
        return imposto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> cadastrarNovoImposto(@RequestBody ImpostoInputDto impostoInputDto) {


        if (impostoInputDto.getNome() == null || impostoInputDto.getNome().toString().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do imposto é obrigatório.");
        }

        if (impostoInputDto.getAliquota() == null || impostoInputDto.getAliquota() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A alíquota deve ser maior que zero.");
        }

        if (impostoInputDto.getDescricao() == null || impostoInputDto.getDescricao().length() > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A descrição é obrigatória e deve ter no máximo 100 caracteres.");
        }

        Imposto imposto = new Imposto();

        imposto.setNome(impostoInputDto.getNome());
        imposto.setDescricao(impostoInputDto.getDescricao());
        imposto.setAliquota(impostoInputDto.getAliquota());

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
    public ResponseEntity<?> deletarImposto(@PathVariable Long id) {

        if (impostoServiceImpl.buscarPorId(id).isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "ID não encontrado"));
        }

        impostoServiceImpl.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
