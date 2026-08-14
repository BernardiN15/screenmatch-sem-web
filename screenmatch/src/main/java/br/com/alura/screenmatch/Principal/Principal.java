package br.com.alura.screenmatch.Principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

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
    private List<DadosSeries> listaSeries =new ArrayList<>();




    public void exibirMenu(){
        var opcao=-1;
       while(opcao!=0) {
           String menu = """
                   1 - Buscar serie
                   2 - Buscar episódio
                   3 - Listar series buscadas
                   
                   0 - Sair
                   """;

           System.out.println(menu);
           System.out.println("diga o seu numero:");
           opcao = leitor.nextInt();
           leitor.nextLine();
           switch (opcao) {
               case 1:
                   buscarSerieWeb();
                   break;
               case 2:
                   episodiosPorSerie();
                   break;
               case 3:
                   listarSeriesBuscadas();
                   break;
               case 0:
                   break;
               default:
                   System.out.println("opção invalida!");
                   break;

           }
       }
//        DadosTemporada dadosTemporada;
//        List<DadosTemporada> listaTemporadas=new ArrayList<>();
//        for(int i=1;i<=getDadosSeries().totalTemporadas();i++){
//             String json=consumoApi.obterDados(ENDERECO +nomeSerie.replace(" ","+")+"&season="+i+API_KEY);
//            dadosTemporada=conversor.obterDados(json,DadosTemporada.class);
//            listaTemporadas.add(dadosTemporada);
//        }
//        listaTemporadas.forEach(System.out::println);
//
//        for(int i=0;i<dados.totalTemporadas();i++){
//            List<DadosEpisodio> episodioTemporada=listaTemporadas.get(i).episodios();
//            for(int j=0;j<episodioTemporada.size();j++){
//                System.out.println(episodioTemporada.get(j));
//            }
//        }

//        listaTemporadas.forEach(t-> t.episodios().forEach(e-> System.out.println(e.titulo())));
//        List<String> nomes= Arrays.asList("Paulo","Ana","Jaque","Eduarda");
//       nomes.stream()
//               .sorted()
//               .limit(3)
//               .forEach(System.out::println);

//        List<DadosEpisodio> listaEpisodios=listaTemporadas.stream()
//                .flatMap(n->n.episodios().stream())
//                .collect(Collectors.toList());

//        listaEpisodios.forEach(System.out::println);
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
//
//        List<Episodio> episodios=listaTemporadas.stream()
//                .flatMap(t->t.episodios().stream()
//                        .map(e->new Episodio(t.numero(),e)))
//                .collect(Collectors.toList());

//        episodios.forEach(System.out::println);
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
    }

    private void listarSeriesBuscadas() {
        List<Serie>series= this.listaSeries.stream()
                        .map(e->new Serie(e))
                .collect(Collectors.toList());

         series.stream()
                         .sorted(Comparator.comparing(Serie::getGenero))
                                 .forEach(System.out::println);


    }

    public void episodiosPorSerie(){
        DadosSeries dadosSerie= getListaSeries();
        DadosTemporada dadosTemporada;
        List<DadosTemporada> listaTemporadas=new ArrayList<>();
        for(int i=1;i<=dadosSerie.totalTemporadas();i++){
            String json=consumoApi.obterDados(ENDERECO +dadosSerie.titulo().replace(" ","+")+"&season="+i+API_KEY);
            dadosTemporada=conversor.obterDados(json,DadosTemporada.class);
            listaTemporadas.add(dadosTemporada);
        }
        List<Episodio> episodios=listaTemporadas.stream()
                .flatMap(t->t.episodios().stream()
                        .map(e->new Episodio(t.numero(),e)))
                .collect(Collectors.toList());
        episodios.forEach(System.out::println);


}

    public DadosSeries getListaSeries(){
        System.out.println("digite o nome da serie para busca:");
        String nomeSerie = leitor.nextLine();
        String json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSeries dados = conversor.obterDados(json, DadosSeries.class);
        return dados;
    }


    public void buscarSerieWeb(){
        DadosSeries dados= getListaSeries();
        System.out.println(dados);
        listaSeries.add(dados);

    }


}
