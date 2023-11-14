package com.meli.partidasfutebol.service;

import com.meli.partidasfutebol.dto.PartidaDto;
import com.meli.partidasfutebol.model.Partida;
import com.meli.partidasfutebol.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PartidaRepository partidaRepository;

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
    public String adicionaPartida(PartidaDto partidaDto){
        if(verificaDuplicidadePartidaPorEstadio(partidaDto.getEstadio(), partidaDto.getDataHora())){
            return "Não pode registrar a partida, pois já existe partida neste dia para o estádio informado";
        }

        if(verificaDuplicidadePartidaPorClube(partidaDto.getNomeClubeMandante(), partidaDto.getNomeClubeVisitante(), partidaDto.getDataHora())){
            return "Não pode registrar a partida, pois já existe partida para este clube no período de dois dias";
        }

        LocalDateTime dataHoraAtual = LocalDateTime.now();
        if(partidaDto.getDataHora().isAfter(dataHoraAtual)){
           return "A data e hora da partida não pode ser maior que a data e hora atual!";
        }

        LocalDateTime horaCadastrada = partidaDto.getDataHora();
        String dataHoraString = horaCadastrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String apenasHoraString = dataHoraString.substring(11, 19);
        LocalTime apenasHora = LocalTime.parse(apenasHoraString);
        LocalTime horaMinimo = LocalTime.parse("08:00");

        LocalDateTime teste = partidaDto.getDataHora().minusDays(2);
        if(apenasHora.isBefore(horaMinimo)){
            return "O horário de início da partida não pode ser antes das 08:00!";
        }

        LocalTime dataHoraMaximo = LocalTime.parse("22:00");
        if(apenasHora.isAfter(dataHoraMaximo)){
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
        return "Partida" + partida.getId() + " adicionada! " + teste;
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

        LocalDateTime localDateTime = LocalDateTime.now();
        if(partidaDto.getDataHora().isAfter(localDateTime)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O horário da partida não pode ser maior que o horário atual!");
        }

        LocalDateTime horaCadastrada = partidaDto.getDataHora();
        String dataHoraString = horaCadastrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String apenasHoraString = dataHoraString.substring(11, 19);
        LocalTime apenasHora = LocalTime.parse(apenasHoraString);
        LocalTime horaMinimo = LocalTime.parse("08:00");
        if(apenasHora.isBefore(horaMinimo)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O horário de início da partida não pode ser antes das 08:00!");
        }

        LocalTime dataHoraMaximo = LocalTime.parse("22:00");
        if(apenasHora.isAfter(dataHoraMaximo)){
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
