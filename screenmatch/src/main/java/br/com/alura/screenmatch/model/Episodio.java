package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temproada;
    private String titulo;
    private String duracao;
    private Double avaliacao;
    private LocalDate datalancamento;

    public Episodio(Integer numero, DadosEpisodio e) {
        this.temproada=numero;
        try {
            this.avaliacao = Double.valueOf(e.avaliacao());
        }catch (NumberFormatException error){
            this.avaliacao=0.0;
        }
        try{
            this.datalancamento=LocalDate.parse(e.datalancamento());
        }catch(DateTimeParseException exception) {
            this.datalancamento=null;
        }
        this.duracao=e.duracao();
        this.titulo=e.titulo();
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getTemproada() {
        return temproada;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public String getDuracao() {
        return duracao;
    }

    public LocalDate getDatalancamento() {
        return datalancamento;
    }

    @Override
    public String toString() {
        return  "temproada=" + temproada +
                ", titulo='" + titulo + '\'' +
                ", duracao='" + duracao + '\'' +
                ", avaliacao=" + avaliacao +
                ", datalancamento=" + datalancamento;
    }
}
