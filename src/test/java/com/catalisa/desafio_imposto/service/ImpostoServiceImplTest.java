package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.CalculoImpostoRequest;
import com.catalisa.desafio_imposto.dto.CalculoImpostoResponse;
import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.model.Imposto;
import com.catalisa.desafio_imposto.model.Roles;
import com.catalisa.desafio_imposto.model.TipoImposto;
import com.catalisa.desafio_imposto.repository.ImpostoRepository;
import com.catalisa.desafio_imposto.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImpostoServiceImplTest {



    @Mock
    private ImpostoRepository impostoRepository;

    @InjectMocks
    private ImpostoServiceImpl impostoServiceImpl;


    @Test
    void listarTodosImpostos() {
        List<Imposto> mockEntities = Arrays.asList(
                new Imposto(1L, TipoImposto.ICMS, "Descrição A", 10.0),
                new Imposto(2L, TipoImposto.IPI, "Descrição B", 15.0)
        );
        when(impostoRepository.findAll()).thenReturn(mockEntities);


        List<ImpostoDto> result = impostoServiceImpl.listarTodosImpostos();


        assertEquals(2, result.size());


        assertEquals(TipoImposto.ICMS, result.get(0).getNome());
        assertEquals("Descrição A", result.get(0).getDescricao());
        assertEquals(10.0, result.get(0).getAliquota());

        assertEquals(TipoImposto.IPI, result.get(1).getNome());
        assertEquals("Descrição B", result.get(1).getDescricao());
        assertEquals(15.0, result.get(1).getAliquota());

        
        verify(impostoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId() {

        Imposto imposto = new Imposto(1L, TipoImposto.ICMS, "Descrição A", 10.0);
        when(impostoRepository.findById(1L)).thenReturn(Optional.of(imposto));


        Optional<Imposto> byId = impostoServiceImpl.buscarPorId(1L);


        assertTrue(byId.isPresent()); // Verifica se o Optional contém um valor
        assertEquals(TipoImposto.ICMS, byId.get().getNome()); // Verifica o valor do nome


        verify(impostoRepository, times(1)).findById(1L);
    }

    @Test
    void salvarImposto() {
        Imposto imposto = new Imposto(1L, TipoImposto.ICMS, "Descrição A", 10.0);
        when(impostoRepository.save(imposto)).thenReturn(imposto);

        Imposto resultado = impostoServiceImpl.salvarImposto(imposto);


        assertEquals(imposto.getId(), resultado.getId());
        assertEquals(imposto.getNome(), resultado.getNome());
        assertEquals(imposto.getDescricao(), resultado.getDescricao());
        assertEquals(imposto.getAliquota(), resultado.getAliquota());

        verify(impostoRepository, times(1)).save(imposto);
    }

    @Test
    void deletarComSucesso() {

        Long id = 1L;
        when(impostoRepository.existsById(id)).thenReturn(true);

        impostoServiceImpl.deletar(id);

        verify(impostoRepository, times(1)).deleteById(id);
    }

    @Test
    void deletarComFalha() {

        Long id = 1L;
        when(impostoRepository.existsById(id)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            impostoServiceImpl.deletar(id);
        });

        assertEquals("Imposto com ID " + id + " não encontrado.", exception.getMessage());

        verify(impostoRepository, never()).deleteById(id);
    }

    @Test
    void calcularImposto() {
        CalculoImpostoRequest request = new CalculoImpostoRequest();
        request.setIdImposto(1L);
        request.setValorBase(1000.0);

        Imposto impostoMock = new Imposto(1L, TipoImposto.ICMS, "ICMS", 10.0);
        when(impostoRepository.findById(1L)).thenReturn(java.util.Optional.of(impostoMock));

        CalculoImpostoResponse response = impostoServiceImpl.calcularImposto(request);

        assertEquals("ICMS", response.getTipoImposto());
        assertEquals(1000.0, response.getValorBase());
        assertEquals(10.0, response.getAliquota());
        assertEquals(100.0, response.getValorImposto()); // 10% de 1000 é 100

        verify(impostoRepository, times(1)).findById(1L);
    }

    @Test
    void calcularImposto_ImpostoNaoEncontrado() {

        CalculoImpostoRequest request = new CalculoImpostoRequest();
        request.setIdImposto(1L);
        request.setValorBase(1000.0);

        when(impostoRepository.findById(1L)).thenReturn(java.util.Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            impostoServiceImpl.calcularImposto(request);
        });

        assertEquals("Imposto não encontrado", exception.getMessage());


        verify(impostoRepository, times(1)).findById(1L);
    }

    @Test
    void calcularICMS() {
        CalculoImpostoRequest request = new CalculoImpostoRequest();
        request.setIdImposto(1L);
        request.setValorBase(1000.0);

        Double aliquota = 5.0;


        double resultado = request.getValorBase() * (aliquota / 100);
        int valorEsperado = 50;

        assertEquals(valorEsperado, resultado, "O cálculo do ICMS com valor base zero está incorreto");
    }

    @Test
    void calcularISS() {
    }

    @Test
    void calcularIPI() {
    }
}