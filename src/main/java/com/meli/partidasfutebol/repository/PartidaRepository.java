package com.meli.partidasfutebol.repository;

import com.meli.partidasfutebol.dto.PartidaDto;
import com.meli.partidasfutebol.model.Partida;
import com.meli.partidasfutebol.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


public interface PartidaRepository extends JpaRepository<Partida, Long> {

    @Query("SELECT p FROM Partida p WHERE p.resultadoClubeMandante = 0 AND p.resultadoClubeVisitante = 0")
    List<Partida> buscaPartidaSemGols();

    @Query("SELECT p FROM Partida p WHERE ABS(p.resultadoClubeMandante - p.resultadoClubeVisitante) >= 3")
    List<Partida> buscaGoleada();

    @Query("SELECT p FROM Partida p WHERE p.estadio = ?1" )
    List<Partida> buscaPartidasPorEstadio(String estadio);

    @Query("SELECT p FROM Partida p WHERE p.nomeClubeMandante = ?1 OR p.nomeClubeVisitante = ?1")
    List<Partida> buscaPartidasPorClube(String clube);

    @Query("SELECT p FROM Partida p WHERE p.nomeClubeMandante = ?1")
    List<Partida> buscaPartidasPorClubeMandante(String clube);

    @Query("SELECT p FROM Partida p WHERE p.nomeClubeVisitante = ?1")
    List<Partida> buscaPartidasPorClubeVisitante(String clube);

    @Query(value = "SELECT p FROM Partida p WHERE p.estadio = :estadioNome AND CAST(p.dataHora AS DATE) = CAST(:dateTime AS DATE)")
    List<Partida> verificaDuplicidadePartidaPorEstadio(String estadioNome, LocalDateTime dateTime);

    @Query(value = "SELECT p FROM Partida p WHERE (p.nomeClubeMandante = :clubeMandante OR p.nomeClubeVisitante = :clubeVisitante) AND CAST(p.dataHora AS DATE) BETWEEN CAST(:dataVerificadaMinima AS DATE) AND CAST(:dataVerificadaMaxima AS DATE)")
    List<Partida> verificaDuplicidadePartidaPorClube(String clubeMandante, String clubeVisitante, LocalDateTime dataVerificadaMinima, LocalDateTime dataVerificadaMaxima);
}
