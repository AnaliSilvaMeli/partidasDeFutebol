package com.meli.partidasfutebol.service;

import com.meli.partidasfutebol.dto.PartidaDto;
import com.meli.partidasfutebol.model.Partida;
import com.meli.partidasfutebol.repository.PartidaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class PartidaServiceTest {
    @Mock
    private PartidaRepository partidaRepository;


    private PartidaService service;

    @Captor
    private ArgumentCaptor<Partida> partidaArgumentCaptor;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        service= new PartidaService(partidaRepository);
    }

    @Test
    void devAdicionarPartidaComSucesso(){
        //cenário
        PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-10-03T10:15:30"), "Maracanã");
        partidaDto.setId(null);

        //acão
        BDDMockito.given(partidaRepository.verificaDuplicidadePartidaPorEstadio(partidaDto.getEstadio(), partidaDto.getDataHora()))
                .willReturn(new ArrayList());
        System.out.println(this.service.adicionaPartida(partidaDto));

        //então
        BDDMockito.then(partidaRepository).should().save(partidaArgumentCaptor.capture());
        Partida partida = partidaArgumentCaptor.getValue();
        Assertions.assertThat(partida.getId()).isNotNull();
        Assertions.assertThat(partida.getNomeClubeMandante()).isEqualTo(partidaDto.getNomeClubeMandante());
        System.out.println(partida);
    }

    @Test
    void devNaodeveAdicionarPartida(){
        //cenário
        PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-10-03T10:15:30"), "Maracanã");
        partidaDto.setId(null);

        //acão
        BDDMockito.given(partidaRepository.verificaDuplicidadePartidaPorEstadio(partidaDto.getEstadio(), partidaDto.getDataHora()))
                .willReturn(List.of(new Partida()));
        String resultado = (this.service.adicionaPartida(partidaDto));

        //então
        Assertions.assertThat(resultado).isEqualTo("Não pode registrar a partida, pois já existe partida neste dia para o estádio informado");
    }

}
