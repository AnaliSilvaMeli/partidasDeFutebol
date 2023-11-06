package com.meli.partidasfutebol.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "partida")
public class Partida {
    @Id
    @Column(name = "idPartida")
    private long id;

    @Column
    private String nomeClube;



    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return id;
    }

    public void setNomeClube(String nomeClube){
        this.nomeClube = nomeClube;
    }

    public String getNomeClube(){
        return nomeClube;
    }
}
