package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosSeries;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.sasl.SaslServer;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi=new ConsumoApi();
		String json= consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=7dce5ea1");
		System.out.println(json);
		ConverteDados conversor=new ConverteDados();
		conversor.obterDados(json, DadosSeries.class);
		DadosSeries dados=conversor.obterDados(json,DadosSeries.class);
		System.out.println(dados);



	}
}
