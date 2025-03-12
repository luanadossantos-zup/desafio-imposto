package com.catalisa.desafio_imposto.controller;

import com.catalisa.desafio_imposto.dto.ImpostoDto;

import com.catalisa.desafio_imposto.dto.ImpostoInputDto;
import com.catalisa.desafio_imposto.model.Imposto;
import com.catalisa.desafio_imposto.model.TipoImposto;
import com.catalisa.desafio_imposto.service.ImpostoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TiposControllerTest {

    @InjectMocks
    private TiposController tiposController;

    @Mock
    private ImpostoServiceImpl impostoServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarTodosImpostos_DeveRetornarListaDeImpostos() {

        // Mock dos dados retornados pelo serviço
        ImpostoDto imposto1 = new ImpostoDto(1L, TipoImposto.IPI, "Descrição A", 10.0);
        ImpostoDto imposto2 = new ImpostoDto(2L, TipoImposto.ISS, "Descrição B", 15.0);
        List<ImpostoDto> mockImpostos = List.of(imposto1, imposto2);

        // Configura o comportamento do mock
        when(impostoServiceImpl.listarTodosImpostos()).thenReturn(mockImpostos);

        // Chama o método do controller
        ResponseEntity<List<ImpostoDto>> response = tiposController.listarTodosImpostos();

        // Verifica o status da resposta
        assertEquals(200, response.getStatusCodeValue());


        // Verifica os valores específicos dos objetos na lista
        assertEquals(TipoImposto.IPI, response.getBody().get(0).getNome());
        assertEquals(TipoImposto.ISS, response.getBody().get(1).getNome());

        // Verifica se o método do serviço foi chamado
        verify(impostoServiceImpl, times(1)).listarTodosImpostos();
    }

    @Test
    void deveRetornarNotFoundQuandoImpostoNaoForEncontrado() {
        // Configurando o mock para retornar Optional.empty() quando o ID não for encontrado
        Long idNaoExistente = 1L;
        when(impostoServiceImpl.buscarPorId(idNaoExistente)).thenReturn(Optional.empty());

        // Chama o método do controller
        ResponseEntity<Imposto> response = tiposController.listarImpostoporId(idNaoExistente);

        // Verifica o status da resposta
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void listarImpostoporId_DeveRetornarImpostoQuandoIdExistir() {
        Long id = 1L;
        Imposto imposto = new Imposto();
        imposto.setId(id);
        imposto.setNome(TipoImposto.ISS);

        Mockito.when(impostoServiceImpl.buscarPorId(id)).thenReturn(Optional.of(imposto));

        ResponseEntity<Imposto> response = tiposController.listarImpostoporId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(imposto, response.getBody());
    }

    @Test
    void listarImpostoporId_DeveRetornarNotFoundQuandoIdNaoExistir() {
        Long id = 1L;

        Mockito.when(impostoServiceImpl.buscarPorId(id)).thenReturn(Optional.empty());

        ResponseEntity<Imposto> response = tiposController.listarImpostoporId(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void cadastrarNovoImposto_DeveRetornarCreatedQuandoDadosValidos() {
        ImpostoInputDto inputDto = new ImpostoInputDto();
        inputDto.setNome(TipoImposto.IPI);
        inputDto.setDescricao("Descrição válida");
        inputDto.setAliquota(10.0);

        Imposto imposto = new Imposto();
        imposto.setId(1L);
        imposto.setNome(inputDto.getNome());
        imposto.setDescricao(inputDto.getDescricao());
        imposto.setAliquota(inputDto.getAliquota());

        Mockito.when(impostoServiceImpl.salvarImposto(Mockito.any(Imposto.class))).thenReturn(imposto);

        ResponseEntity<?> response = tiposController.cadastrarNovoImposto(inputDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ImpostoDto responseBody = (ImpostoDto) response.getBody();
        assertEquals(imposto.getId(), responseBody.getId());
        assertEquals(imposto.getNome(), responseBody.getNome());
    }

    @Test
    void cadastrarNovoImposto_DeveRetornarBadRequestQuandoNomeForNulo() {
        ImpostoInputDto inputDto = new ImpostoInputDto();
        inputDto.setNome(null);
        inputDto.setDescricao("Descrição válida");
        inputDto.setAliquota(10.0);

        ResponseEntity<?> response = tiposController.cadastrarNovoImposto(inputDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O nome do imposto é obrigatório.", response.getBody());
    }

    @Test
    void cadastrarNovoImposto_DeveRetornarBadRequestQuandoAliquotaForInvalida() {
        ImpostoInputDto inputDto = new ImpostoInputDto();
        inputDto.setNome(TipoImposto.IPI);
        inputDto.setDescricao("Descrição válida");
        inputDto.setAliquota(0.0);

        ResponseEntity<?> response = tiposController.cadastrarNovoImposto(inputDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("A alíquota deve ser maior que zero.", response.getBody());
    }


    @Test
    void deletarImposto_DeveRetornarNoContentQuandoIdExistir() {
        Long id = 1L;

        Mockito.when(impostoServiceImpl.buscarPorId(id)).thenReturn(Optional.of(new Imposto()));

        ResponseEntity<?> response = tiposController.deletarImposto(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(impostoServiceImpl, Mockito.times(1)).deletar(id);
    }

    @Test
    void deletarImposto_DeveRetornarNotFoundQuandoIdNaoExistir() {
        Long id = 1L;

        Mockito.when(impostoServiceImpl.buscarPorId(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = tiposController.deletarImposto(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(Map.of("error", "ID não encontrado"), response.getBody());
        Mockito.verify(impostoServiceImpl, Mockito.never()).deletar(id);
    }
}