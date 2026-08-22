package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsultaGemini;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
@Entity
@Table(name = "Series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String poster;
    private String sinopse;
    private String atores;
    @Transient
    private List<Episodio> episodios=new ArrayList<>();


    public Serie(DadosSeries e){
        this.titulo = e.titulo();
        this.totalTemporadas = e.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(e.avaliacao())).orElse(0.0);
        this.genero = Categoria.fromString(e.genero().split(",")[0].trim());
        this.poster = e.poster();
        this.sinopse = ConsultaGemini.traduzir(e.sinopse().trim());
        this.atores = e.atores();


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    @Override
    public String toString() {
        return  "genero=" + genero +
                ", titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", poster='" + poster + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", atores='" + atores + '\'';
    }
}

