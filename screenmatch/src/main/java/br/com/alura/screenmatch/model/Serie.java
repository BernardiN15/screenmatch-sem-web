package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsultaGemini;

import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria genero;
    private String poster;
    private String sinopse;
    private String atores;
    private ConsultaGemini consultaGemini =new ConsultaGemini();

    public Serie(DadosSeries e){
        this.titulo = e.titulo();
        this.totalTemporadas = e.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(e.avaliacao())).orElse(0.0);
        this.genero = Categoria.fromString(e.genero().split(",")[0].trim());
        this.poster = e.poster();
        this.sinopse = consultaGemini.traduzir(e.sinopse().trim());
        this.atores = e.atores();


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

