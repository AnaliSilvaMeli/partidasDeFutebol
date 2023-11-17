package com.meli.partidasfutebol.service;

import com.meli.partidasfutebol.dto.PartidaDto;
import com.meli.partidasfutebol.model.Partida;
import com.meli.partidasfutebol.repository.PartidaRepository;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.willReturn;


@SpringBootTest
public class PartidaServiceTest {
    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private PartidaService partidaService;


    private PartidaService service;

    @Captor
    private ArgumentCaptor<Partida> partidaArgumentCaptor;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        service= new PartidaService(partidaRepository);
    }

    @Test
    void deveAdicionarPartidaComSucessoDuplicidadePartidaPorEstadio(){
        //cenário
        PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-10-03T10:15:30"), "Maracanã");
        partidaDto.setId(null);

        //acão
        BDDMockito.given(partidaRepository.verificaDuplicidadePartidaPorEstadio(partidaDto.getEstadio(), partidaDto.getDataHora()))
                .willReturn(new ArrayList());
        this.service.adicionaPartida(partidaDto);

        //então
        BDDMockito.then(partidaRepository).should().save(partidaArgumentCaptor.capture());
        Partida partida = partidaArgumentCaptor.getValue();
        Assertions.assertThat(partida.getId()).isNotNull();
        Assertions.assertThat(partida.getNomeClubeMandante()).isEqualTo(partidaDto.getNomeClubeMandante());
        Assertions.assertThat(partida.getResultadoClubeMandante()).isEqualTo(partidaDto.getResultadoClubeMandante());
        Assertions.assertThat(partida.getNomeClubeVisitante()).isEqualTo(partidaDto.getNomeClubeVisitante());
        Assertions.assertThat(partida.getResultadoClubeVisitante()).isEqualTo(partidaDto.getResultadoClubeVisitante());
        Assertions.assertThat(partida.getDataHora()).isEqualTo(partidaDto.getDataHora());
        Assertions.assertThat(partida.getEstadio()).isEqualTo(partidaDto.getEstadio());
    }

    @Test
    void deveAdicionarPartidaComSucessoDuplicidadeClube(){
        //cenário
        PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-10-03T10:15:30"), "Maracanã");
        partidaDto.setId(null);
        LocalDateTime dataVerificadaMinima = partidaDto.getDataHora().minusDays(2);
        LocalDateTime dataVerificadaMaxima = partidaDto.getDataHora().plusDays(2);
        //acão
        BDDMockito.given(partidaRepository.verificaDuplicidadePartidaPorClube(partidaDto.getNomeClubeMandante(), partidaDto.getNomeClubeVisitante(), dataVerificadaMinima, dataVerificadaMaxima))
                .willReturn(new ArrayList());
        this.service.adicionaPartida(partidaDto);

        //então
        BDDMockito.then(partidaRepository).should().save(partidaArgumentCaptor.capture());
        Partida partida = partidaArgumentCaptor.getValue();
        Assertions.assertThat(partida.getId()).isNotNull();
        Assertions.assertThat(partida.getNomeClubeMandante()).isEqualTo(partidaDto.getNomeClubeMandante());
        Assertions.assertThat(partida.getResultadoClubeMandante()).isEqualTo(partidaDto.getResultadoClubeMandante());
        Assertions.assertThat(partida.getNomeClubeVisitante()).isEqualTo(partidaDto.getNomeClubeVisitante());
        Assertions.assertThat(partida.getResultadoClubeVisitante()).isEqualTo(partidaDto.getResultadoClubeVisitante());
        Assertions.assertThat(partida.getDataHora()).isEqualTo(partidaDto.getDataHora());
        Assertions.assertThat(partida.getEstadio()).isEqualTo(partidaDto.getEstadio());
    }

    @Test
    void deveAdicionarPartidaPorDataHoraMaiorAtual(){
        //cenário
        PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-10-03T10:15:30"), "Maracanã");
        partidaDto.setId(null);
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        //acão
        BDDMockito.given(partidaService.verificaCadastroAposDataHoraAtual(dataHoraAtual))
                .willReturn(Boolean.valueOf(String.valueOf(new Partida())));
        this.service.adicionaPartida(partidaDto);

        //então
        BDDMockito.then(partidaRepository).should().save(partidaArgumentCaptor.capture());
        Partida partida = partidaArgumentCaptor.getValue();
        Assertions.assertThat(partida.getId()).isNotNull();
        Assertions.assertThat(partida.getNomeClubeMandante()).isEqualTo(partidaDto.getNomeClubeMandante());
        Assertions.assertThat(partida.getResultadoClubeMandante()).isEqualTo(partidaDto.getResultadoClubeMandante());
        Assertions.assertThat(partida.getNomeClubeVisitante()).isEqualTo(partidaDto.getNomeClubeVisitante());
        Assertions.assertThat(partida.getResultadoClubeVisitante()).isEqualTo(partidaDto.getResultadoClubeVisitante());
        Assertions.assertThat(partida.getDataHora()).isEqualTo(partidaDto.getDataHora());
        Assertions.assertThat(partida.getEstadio()).isEqualTo(partidaDto.getEstadio());
    }

    @Test
    void deveAdicionarPartidaAntesHoraPossivel(){
        //cenário
        PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-10-03T08:00:30"), "Maracanã");
        partidaDto.setId(null);
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        //acão
        BDDMockito.given(partidaService.verificaCadastroAntesHoraPossivel(dataHoraAtual))
                .willReturn(Boolean.valueOf(String.valueOf(new Partida())));
        this.service.adicionaPartida(partidaDto);

        //então
        BDDMockito.then(partidaRepository).should().save(partidaArgumentCaptor.capture());
        Partida partida = partidaArgumentCaptor.getValue();
        Assertions.assertThat(partida.getId()).isNotNull();
        Assertions.assertThat(partida.getNomeClubeMandante()).isEqualTo(partidaDto.getNomeClubeMandante());
        Assertions.assertThat(partida.getResultadoClubeMandante()).isEqualTo(partidaDto.getResultadoClubeMandante());
        Assertions.assertThat(partida.getNomeClubeVisitante()).isEqualTo(partidaDto.getNomeClubeVisitante());
        Assertions.assertThat(partida.getResultadoClubeVisitante()).isEqualTo(partidaDto.getResultadoClubeVisitante());
        Assertions.assertThat(partida.getDataHora()).isEqualTo(partidaDto.getDataHora());
        Assertions.assertThat(partida.getEstadio()).isEqualTo(partidaDto.getEstadio());
    }

    @Test
    void deveAdicionarPartidaDepoisHoraPossivel(){
        //cenário
        PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-10-03T08:00:30"), "Maracanã");
        partidaDto.setId(null);
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        //acão
        BDDMockito.given(partidaService.verificaCadastroDepoisHoraPossivel(dataHoraAtual))
                .willReturn(Boolean.valueOf(String.valueOf(new Partida())));
        this.service.adicionaPartida(partidaDto);

        //então
        BDDMockito.then(partidaRepository).should().save(partidaArgumentCaptor.capture());
        Partida partida = partidaArgumentCaptor.getValue();
        Assertions.assertThat(partida.getId()).isNotNull();
        Assertions.assertThat(partida.getNomeClubeMandante()).isEqualTo(partidaDto.getNomeClubeMandante());
        Assertions.assertThat(partida.getResultadoClubeMandante()).isEqualTo(partidaDto.getResultadoClubeMandante());
        Assertions.assertThat(partida.getNomeClubeVisitante()).isEqualTo(partidaDto.getNomeClubeVisitante());
        Assertions.assertThat(partida.getResultadoClubeVisitante()).isEqualTo(partidaDto.getResultadoClubeVisitante());
        Assertions.assertThat(partida.getDataHora()).isEqualTo(partidaDto.getDataHora());
        Assertions.assertThat(partida.getEstadio()).isEqualTo(partidaDto.getEstadio());
    }

    @Test
    void naoDeveAdicionarPartidaPorDuplicidadeEstadio() {
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

        @Test
        void naoDeveAdicionarPartidaPorDuplicidadeClube() {
            //cenário
            PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-10-03T10:15:30"), "Maracanã");
            partidaDto.setId(null);
            LocalDateTime dataVerificadaMinima = partidaDto.getDataHora().minusDays(2);
            LocalDateTime dataVerificadaMaxima = partidaDto.getDataHora().plusDays(2);

            //ação
            BDDMockito.given(partidaRepository.verificaDuplicidadePartidaPorClube(partidaDto.getNomeClubeMandante(), partidaDto.getNomeClubeVisitante(), dataVerificadaMinima, dataVerificadaMaxima))
                    .willReturn(List.of(new Partida()));
            String resultado2 = (this.service.adicionaPartida(partidaDto));

            //então
            Assertions.assertThat(resultado2).isEqualTo("Não pode registrar a partida, pois já existe partida para este clube no período de dois dias");
        }

    @Test
    void naoDeveAdicionarPartidaPorDataHoraMaiorAtual() {
        //cenário
        PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-11-18T16:15:30"), "Maracanã");
        partidaDto.setId(null);
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        //ação
        BDDMockito.given(partidaService.verificaCadastroAposDataHoraAtual(dataHoraAtual))
                .willReturn(Boolean.valueOf(String.valueOf(new Partida())));
        String resultado3 = (this.service.adicionaPartida(partidaDto));

        //então
        Assertions.assertThat(resultado3).isEqualTo("A data e hora da partida não pode ser maior que a data e hora atual!");
    }

    @Test
    void naoDeveAdicionarPartidaAntesHoraPossivel() {
        //cenário
        PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-11-17T07:59:30"), "Maracanã");
        partidaDto.setId(null);

        //ação
        BDDMockito.given(partidaService.verificaCadastroAntesHoraPossivel(partidaDto.getDataHora()))
                .willReturn(Boolean.valueOf(String.valueOf(new Partida())));
        String resultado3 = (this.service.adicionaPartida(partidaDto));

        //então
        Assertions.assertThat(resultado3).isEqualTo("O horário de início da partida não pode ser antes das 08:00!");
    }

    @Test
    void naoDeveAdicionarPartidaDepoisHoraPossivel() {
        //cenário
        PartidaDto partidaDto = new PartidaDto(1L, "Vasco", 2, "Corinthians", 5, LocalDateTime.parse("2023-11-16T22:01:00"), "Maracanã");
        partidaDto.setId(null);

        //ação
        BDDMockito.given(partidaService.verificaCadastroDepoisHoraPossivel(partidaDto.getDataHora()))
                .willReturn(Boolean.valueOf(String.valueOf(new Partida())));
        String resultado3 = (this.service.adicionaPartida(partidaDto));

        //então
        Assertions.assertThat(resultado3).isEqualTo("O horário de início da partida não pode ser após às 22:00!");
    }


    }
