package com.meli.partidasfutebol.controller;

import com.meli.partidasfutebol.dto.PartidaDto;
import com.meli.partidasfutebol.model.Partida;
import com.meli.partidasfutebol.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("partida")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    //@GetMapping
    //public String adicionaPartida(){
    //    return partidaService.adicionaPartida();
    //}

    @PostMapping
    public String adicionaPartida(@RequestBody PartidaDto partidaDto) {
        return partidaService.adicionaPartida(partidaDto);

    }
}
