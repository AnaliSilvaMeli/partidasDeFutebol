package com.meli.partidasfutebol.controller;

import com.meli.partidasfutebol.dto.PartidaDto;
import com.meli.partidasfutebol.model.Partida;
import com.meli.partidasfutebol.repository.PartidaRepository;
import com.meli.partidasfutebol.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("partida")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private PartidaRepository partidaRepository;

    @GetMapping("porEstadio/{estadio}")
    public ResponseEntity<?> listarPartidas(@PathVariable("estadio")String estadio){
        return ResponseEntity.ok(this.partidaRepository.buscaPartidasPorEstadio(estadio));
    }

    @GetMapping("porClube/{clube}")
    public ResponseEntity<?> buscaPartidasPorClube(@PathVariable("clube")String clube){
        return ResponseEntity.ok(this.partidaRepository.buscaPartidasPorClube(clube));
    }

    @GetMapping("porClubeMandante/{clube}")
    public ResponseEntity<?> buscaPartidasPorClubeMandante(@PathVariable("clube")String clube){
        return ResponseEntity.ok(this.partidaRepository.buscaPartidasPorClubeMandante(clube));
    }

    @GetMapping("porClubeVisitante/{clube}")
    public ResponseEntity<?> buscaPartidasPorClubeVisitante(@PathVariable("clube")String clube){
        return ResponseEntity.ok(this.partidaRepository.buscaPartidasPorClubeVisitante(clube));
    }

    @GetMapping("partidaSemGols")
    public List<Partida> partidaSemGols(){
       return partidaRepository.buscaPartidaSemGols();
    }

    @GetMapping("buscaGoleada")
    public List<Partida> buscaGoleada(){
        return partidaRepository.buscaGoleada();
    }

    @PostMapping
    public String adicionaPartida(@Valid @RequestBody PartidaDto partidaDto) {
        return partidaService.adicionaPartida(partidaDto);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity atualizaPartida(@PathVariable("id") long id,
                                @Valid @RequestBody PartidaDto partidaDto) {
        return partidaService.atualizaPartida(id, partidaDto);
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity <?> deletaPartida(@PathVariable long id) {
        return partidaService.deletaPartida(id);
    }
}
