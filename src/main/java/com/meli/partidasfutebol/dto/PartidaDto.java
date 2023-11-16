package com.meli.partidasfutebol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@NoArgsConstructor
@AllArgsConstructor
public class PartidaDto {
    private Long id;
    @NotBlank
    private String nomeClubeMandante;

    @NotNull
    @Min(value = 0)
    private int resultadoClubeMandante;
    @NotBlank
    private String nomeClubeVisitante;
    @NotNull
    @Min(value = 0)
    private int resultadoClubeVisitante;

    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
    private LocalDateTime dataHora;
    @NotBlank
    private String estadio;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
