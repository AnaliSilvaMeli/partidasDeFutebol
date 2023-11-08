package com.meli.partidasfutebol.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "partida")
public class Partida {
    @Id
    @Column(name = "idPartida")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String nomeClubeMandante;

    @Column
    private int  resultadoClubeMandante;

    @Column
    private String nomeClubeVisitante;

    @Column
    private int  resultadoClubeVisitante;

    @Column
    private LocalDateTime dataHora;

    @Column
    private String estadio;

    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return id;
    }

    public String getNomeClubeMandante() {
        return nomeClubeMandante;
    }

    public void setNomeClubeMandante(String nomeClubeMandante) {
        this.nomeClubeMandante = nomeClubeMandante;
    }

    public int getResultadoClubeMandante() {
        return resultadoClubeMandante;
    }

    public void setResultadoClubeMandante(int resultadoClubeMandante) {
        this.resultadoClubeMandante = resultadoClubeMandante;
    }

    public String getNomeClubeVisitante() {
        return nomeClubeVisitante;
    }

    public void setNomeClubeVisitante(String nomeClubeVisitante) {
        this.nomeClubeVisitante = nomeClubeVisitante;
    }

    public int getResultadoClubeVisitante() {
        return resultadoClubeVisitante;
    }

    public void setResultadoClubeVisitante(int resultadoClubeVisitante) {
        this.resultadoClubeVisitante = resultadoClubeVisitante;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }
}
