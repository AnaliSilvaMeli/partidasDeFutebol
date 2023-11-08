package com.meli.partidasfutebol.service;

import com.meli.partidasfutebol.dto.PartidaDto;
import com.meli.partidasfutebol.model.Partida;
import com.meli.partidasfutebol.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    public String adicionaPartida(PartidaDto partidaDto){
        Partida partida = new Partida();
        partida.setNomeClubeMandante(partidaDto.getNomeClubeMandante());
        partida.setResultadoClubeMandante(partidaDto.getResultadoClubeMandante());
        partida.setNomeClubeVisitante(partidaDto.getNomeClubeVisitante());
        partida.setResultadoClubeVisitante(partidaDto.getResultadoClubeVisitante());
        partida.setDataHora(partidaDto.getDataHora());
        partida.setEstadio(partidaDto.getEstadio());
        partidaRepository.save(partida);
        return "Partida" + partida.getId() + " adicionada!";
    }
}
