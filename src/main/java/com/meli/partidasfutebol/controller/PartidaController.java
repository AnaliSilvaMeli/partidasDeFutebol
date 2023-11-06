package com.meli.partidasfutebol.controller;

import com.meli.partidasfutebol.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("partida")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @GetMapping
    public String adicionaPartida(){
        return partidaService.adicionaPartida();
    }
}
