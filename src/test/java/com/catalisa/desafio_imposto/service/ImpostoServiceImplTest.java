package com.catalisa.desafio_imposto.service;

import com.catalisa.desafio_imposto.dto.ImpostoDto;
import com.catalisa.desafio_imposto.model.Imposto;
import com.catalisa.desafio_imposto.model.Roles;
import com.catalisa.desafio_imposto.model.TipoImposto;
import com.catalisa.desafio_imposto.repository.ImpostoRepository;
import com.catalisa.desafio_imposto.repository.UsuarioRepository;
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
    private ImpostoService impostoService;


    @Test
    void listarTodosImpostos() {
        List<Imposto> mockEntities = Arrays.asList(
                new Imposto(1L, TipoImposto.ICMS, "Descrição A", 10.0),
                new Imposto(2L, TipoImposto.IPI, "Descrição B", 15.0)
        );
        when(impostoRepository.findAll()).thenReturn(mockEntities);


        List<ImpostoDto> result = impostoService.listarTodosImpostos();


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


        Optional<Imposto> byId = impostoService.buscarPorId(1L);


        assertTrue(byId.isPresent()); // Verifica se o Optional contém um valor
        assertEquals(TipoImposto.ICMS, byId.get().getNome()); // Verifica o valor do nome


        verify(impostoRepository, times(1)).findById(1L);
    }

    @Test
    void salvarImposto() {
    }

    @Test
    void deletar() {
    }

    @Test
    void calcularImposto() {
    }

    @Test
    void calcularICMS() {
    }

    @Test
    void calcularISS() {
    }

    @Test
    void calcularIPI() {
    }
}