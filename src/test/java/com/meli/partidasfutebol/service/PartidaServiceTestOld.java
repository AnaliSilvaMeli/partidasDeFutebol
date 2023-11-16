package com.meli.partidasfutebol.service;
import com.meli.partidasfutebol.dto.PartidaDto;
import com.meli.partidasfutebol.model.Partida;
import com.meli.partidasfutebol.repository.PartidaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

//@ExtendWith(MockitoExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PartidaServiceTestOld {
    @InjectMocks
    private PartidaService partidaService;

    @Mock
    private PartidaRepository partidaRepository;

    PartidaDto partidaDto;

    //@BeforeAll
    //public void setUp(){
    //    partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-10-03T10:15:30"), "Maracanã");
    //}
    //public PartidaDto adicionaPartida(PartidaDto partidaDto) {
    //    partidaDto.setId(1L);
    //    partidaDto.setNomeClubeMandante("Vasco");
    //    partidaDto.setResultadoClubeMandante(2);
    //   partidaDto.setNomeClubeVisitante("Corinthians");
    //    partidaDto.setResultadoClubeVisitante(5);
    //    partidaDto.setDataHora(LocalDateTime.parse("2023-10-03T10:15:30"));
    //    partidaDto.setEstadio("Maracanã");
    //    return partidaDto;
    //}


    @DisplayName("Deve cadastrar uma Partida")
     void validaCadastroPartidaComSucesso(){
        partidaService.adicionaPartida(partidaDto);
        Optional <Partida> partida = Optional.of(criarPartida());

        //Mockito.when(partidaRepository.save(ArgumentMatchers.eq(partidaDto))).thenReturn(partida);
    }

    private Partida criarPartida() {
        Partida partida = Mockito.mock(Partida.class);
        //Mockito.when(partida.getResultadoClubeMandante()).thenReturn(String);
        return partida;
    }

    //public void teste1(){
    //    PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-10-03T10:15:30"), "Maracanã");
    //    String result = partidaService.adicionaPartida(partidaDto);
    //    Assertions.assertEquals(result, partidaDto);
    //}


}
