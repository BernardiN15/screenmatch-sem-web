package br.com.alura.screenmatch;

import br.com.alura.screenmatch.Principal.Principal;
import br.com.alura.screenmatch.Repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
	@Autowired
	SerieRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal=new Principal(repositorio);
		principal.exibirMenu();

	}
}
