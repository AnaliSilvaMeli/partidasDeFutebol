package com.meli.partidasfutebol;

import com.meli.partidasfutebol.repository.PartidaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PartidasFutebolApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartidasFutebolApplication.class, args);
    }
}
