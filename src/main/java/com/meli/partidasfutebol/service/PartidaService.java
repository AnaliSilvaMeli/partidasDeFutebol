package com.meli.partidasfutebol.service;

import com.meli.partidasfutebol.dto.PartidaDto;
import com.meli.partidasfutebol.model.Partida;
import com.meli.partidasfutebol.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public ResponseEntity<?> deletaPartida(long id) {
        Optional<Partida> partidaOptional = partidaRepository.findById(id);
        if(!partidaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partida não encontrada!");
        }
        return partidaRepository.findById(id)
                .map(record -> {
                    partidaRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity atualizaPartida(long id, PartidaDto partidaDto) {
        Optional<Partida> partidaOptional = partidaRepository.findById(id);
        if(!partidaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partida não encontrada!");
        }
        return partidaRepository.findById(id)
                .map(item -> {
                    item.setNomeClubeMandante(partidaDto.getNomeClubeMandante());
                    item.setResultadoClubeMandante(partidaDto.getResultadoClubeMandante());
                    item.setNomeClubeVisitante(partidaDto.getNomeClubeVisitante());
                    item.setResultadoClubeVisitante(partidaDto.getResultadoClubeVisitante());
                    item.setDataHora(partidaDto.getDataHora());
                    item.setEstadio(partidaDto.getEstadio());
                    Partida atualizaPartida = partidaRepository.save(item);
                    return ResponseEntity.ok().body(atualizaPartida);
                }).orElse(ResponseEntity.notFound().build());
    }
}
