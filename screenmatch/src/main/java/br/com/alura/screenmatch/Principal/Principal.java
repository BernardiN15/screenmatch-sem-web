package br.com.alura.screenmatch.Principal;

import br.com.alura.screenmatch.model.DadosSeries;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitor=new Scanner(System.in);
    private final String ENDERECO="https://www.omdbapi.com/?t=";
    private final String API_KEY="&apikey=7dce5ea1";
    private ConsumoApi consumoApi=new ConsumoApi();
    private ConverteDados conversor=new ConverteDados();



    public void exibirMenu(){
        System.out.println("digite o nome da serie para busca:");
        String nomeSerie=leitor.nextLine();
        String json= consumoApi.obterDados(ENDERECO +nomeSerie.replace(" ","+")+API_KEY);
        DadosSeries dados=conversor.obterDados(json,DadosSeries.class);
        System.out.println(dados);
        DadosTemporada dadosTemporada;
        List<DadosTemporada> listaTemporadas=new ArrayList<>();
        for(int i=1;i<=dados.totalTemporadas();i++){
            json=consumoApi.obterDados(ENDERECO +nomeSerie.replace(" ","+")+"&season="+i+API_KEY);
            dadosTemporada=conversor.obterDados(json,DadosTemporada.class);
            listaTemporadas.add(dadosTemporada);
        }
        listaTemporadas.forEach(System.out::println);


    }
}
