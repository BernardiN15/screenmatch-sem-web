package br.com.alura.screenmatch.Principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSeries;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitor=new Scanner(System.in);
    private final String ENDERECO="https://www.omdbapi.com/?t=";
    private final String API_KEY="&apikey=7dce5ea1";
    private ConsumoApi consumoApi=new ConsumoApi();
    private ConverteDados conversor=new ConverteDados();
    private final DateTimeFormatter  FORMATTER =DateTimeFormatter.ofPattern("dd/MM/yyyy");



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

        for(int i=0;i<dados.totalTemporadas();i++){
            List<DadosEpisodio> episodioTemporada=listaTemporadas.get(i).episodios();
            for(int j=0;j<episodioTemporada.size();j++){
                System.out.println(episodioTemporada.get(j));
            }
        }

//        listaTemporadas.forEach(t-> t.episodios().forEach(e-> System.out.println(e.titulo())));
//        List<String> nomes= Arrays.asList("Paulo","Ana","Jaque","Eduarda");
//       nomes.stream()
//               .sorted()
//               .limit(3)
//               .forEach(System.out::println);

        List<DadosEpisodio> listaEpisodios=listaTemporadas.stream()
                .flatMap(n->n.episodios().stream())
                .collect(Collectors.toList());

        listaEpisodios.forEach(System.out::println);
//        System.out.println("\n top 10 episodios: ");
//        listaEpisodios.stream()
//                .filter(n->!n.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e-> System.out.println("primeiro filtro N/A "+e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e-> System.out.println("ordenação"+e))
//                .limit(10)
//                .peek(e-> System.out.println("limite"+e))
//                .map(n->n.titulo().toUpperCase())
//                .peek(e-> System.out.println("mapeamento "+e))
//                .forEach(System.out::println);

        List<Episodio> episodios=listaTemporadas.stream()
                .flatMap(t->t.episodios().stream()
                        .map(e->new Episodio(t.numero(),e)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);
//        System.out.println("A partir de que ano voce deseja ver os episodios ?");
//        var ano=leitor.nextInt();
//        leitor.nextLine();
//        LocalDate dataBusca=LocalDate.of(ano,1,1);
//        episodios.stream()
//                .filter(e->e.getDatalancamento().isAfter(dataBusca) && e.getDatalancamento()!=null)
//                .forEach(e-> System.out.println(
//                        "temporada: "+e.getTemproada()+
//                                " episódio: "+e.getTitulo()+
//                                " data lançamento: "+e.getDatalancamento().format(FORMATTER)
//
//                ));

//        System.out.println("Digite o episodio ");
//        var trechoTitulo=leitor.nextLine();
//        Optional<Episodio>episodioBuscado=episodios.stream()
//                .filter(e->e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado!");
//            System.out.println("Temporada: "+episodioBuscado.get().getTemproada());
//        }else{
//            System.out.println("Episódio não encontrado!");
//        }


        Map<Integer,Double>avalicoesPorTemporada=episodios.stream()
                .filter(e->e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemproada,Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avalicoesPorTemporada);

    }
}
