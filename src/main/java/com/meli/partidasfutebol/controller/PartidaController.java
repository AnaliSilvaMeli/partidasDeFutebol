package com.meli.partidasfutebol.controller;

import com.meli.partidasfutebol.dto.PartidaDto;
import com.meli.partidasfutebol.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("partida")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

   // @GetMapping("buscaPorEstadio")
   // public ResponseEntity<Object> bus


    @PostMapping
    public String adicionaPartida(@RequestBody PartidaDto partidaDto) {
        return partidaService.adicionaPartida(partidaDto);

    }

    @PutMapping(value="/{id}")
    public ResponseEntity atualizaPartida(@PathVariable("id") long id,
                                 @RequestBody PartidaDto partidaDto) {
        return partidaService.atualizaPartida(id, partidaDto);
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity <?> deletaPartida(@PathVariable long id) {
        return partidaService.deletaPartida(id);
    }
}
