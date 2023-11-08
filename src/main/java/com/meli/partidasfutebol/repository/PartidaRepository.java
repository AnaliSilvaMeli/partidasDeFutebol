package com.meli.partidasfutebol.repository;

import com.meli.partidasfutebol.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PartidaRepository extends JpaRepository<Partida, Long> {
    @Query("SELECT s FROM Partida s WHERE s.estadio = ?1")
    Optional<Partida> buscaPartidasPorEstadio(String estadio);
}
