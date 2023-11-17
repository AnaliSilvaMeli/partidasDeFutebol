package com.meli.partidasfutebol.service;

import com.meli.partidasfutebol.dto.PartidaDto;
import com.meli.partidasfutebol.model.Partida;
import com.meli.partidasfutebol.repository.PartidaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PartidaService {

    private PartidaRepository partidaRepository;
    public PartidaService(PartidaRepository partidaRepository){
        this.partidaRepository = partidaRepository;
    }

    private Boolean verificaDuplicidadePartidaPorEstadio(String estadioNome , LocalDateTime dateTime){
        List<Partida> listaPartidasEstadio = partidaRepository.verificaDuplicidadePartidaPorEstadio(estadioNome,dateTime);
        return listaPartidasEstadio.size() > 0;
    }

    private Boolean verificaDuplicidadePartidaPorClube(String clubeMandante, String clubeVisitante, LocalDateTime dateTime){
        LocalDateTime dataVerificadaMinima = dateTime.minusDays(2);
        LocalDateTime dataVerificadaMaxima = dateTime.plusDays(2);
        List<Partida> listaPartidasClube = partidaRepository.verificaDuplicidadePartidaPorClube(clubeMandante, clubeVisitante, dataVerificadaMinima, dataVerificadaMaxima);
        return listaPartidasClube.size() > 0;
    }

    public Boolean verificaCadastroAposDataHoraAtual(LocalDateTime dateTime){
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        return dateTime.isAfter(dataHoraAtual);

    }

    public Boolean verificaCadastroAntesHoraPossivel(LocalDateTime data){
        String dataHoraString = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String apenasHoraString = dataHoraString.substring(11, 19);
        LocalTime apenasHora = LocalTime.parse(apenasHoraString);
        LocalTime horaMinimo = LocalTime.parse("08:00");
        return apenasHora.isBefore(horaMinimo);
    }
    public Boolean verificaCadastroDepoisHoraPossivel(LocalDateTime data){
        String dataHoraString = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String apenasHoraString = dataHoraString.substring(11, 19);
        LocalTime apenasHora = LocalTime.parse(apenasHoraString);
        LocalTime dataHoraMaximo = LocalTime.parse("22:00");
        return apenasHora.isAfter(dataHoraMaximo);
    }

    public String adicionaPartida(PartidaDto partidaDto){
        if(verificaDuplicidadePartidaPorEstadio(partidaDto.getEstadio(), partidaDto.getDataHora())){
            return "Não pode registrar a partida, pois já existe partida neste dia para o estádio informado";
        }

        if(verificaDuplicidadePartidaPorClube(partidaDto.getNomeClubeMandante(), partidaDto.getNomeClubeVisitante(), partidaDto.getDataHora())){
            return "Não pode registrar a partida, pois já existe partida para este clube no período de dois dias";
        }

        if(verificaCadastroAposDataHoraAtual(partidaDto.getDataHora())){
            return "A data e hora da partida não pode ser maior que a data e hora atual!";
        }

        if(verificaCadastroAntesHoraPossivel(partidaDto.getDataHora())){
            return "O horário de início da partida não pode ser antes das 08:00!";
        }


        if(verificaCadastroDepoisHoraPossivel(partidaDto.getDataHora())){
            return "O horário de início da partida não pode ser após às 22:00!";
        }

        Partida partida = new Partida();
        partida.setNomeClubeMandante(partidaDto.getNomeClubeMandante());
        partida.setResultadoClubeMandante(partidaDto.getResultadoClubeMandante());
        partida.setNomeClubeVisitante(partidaDto.getNomeClubeVisitante());
        partida.setResultadoClubeVisitante(partidaDto.getResultadoClubeVisitante());
        partida.setDataHora(partidaDto.getDataHora());
        partida.setEstadio(partidaDto.getEstadio());
        partidaRepository.save(partida);
        return "Partida " + partida.getId() + " adicionada! ";
    }

    public ResponseEntity<?> deletaPartida(long id) {
        Optional<Partida> partidaOptional = partidaRepository.findById(id);
        if(!partidaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partida não encontrada!");
        }
        return partidaRepository.findById(id)
                .map(record -> {
                    partidaRepository.deleteById(id);
                    return ResponseEntity.ok().body("Partida deletada do banco de dados!");
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity atualizaPartida(long id, PartidaDto partidaDto) {
        if(verificaDuplicidadePartidaPorEstadio(partidaDto.getEstadio(), partidaDto.getDataHora())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não pode registrar a partida, pois já existe partida neste dia para o estádio informado");
        }

        if(verificaDuplicidadePartidaPorClube(partidaDto.getNomeClubeMandante(), partidaDto.getNomeClubeVisitante(), partidaDto.getDataHora())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não pode registrar a partida, pois já existe partida para este clube no período de dois dias");
        }

        if(verificaCadastroAposDataHoraAtual(partidaDto.getDataHora())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O horário da partida não pode ser maior que o horário atual!");
        }

        if(verificaCadastroAntesHoraPossivel(partidaDto.getDataHora())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O horário de início da partida não pode ser antes das 08:00!");
        }

        if(verificaCadastroDepoisHoraPossivel(partidaDto.getDataHora())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O horário de início da partida não pode ser após às 22:00!");
        }

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
