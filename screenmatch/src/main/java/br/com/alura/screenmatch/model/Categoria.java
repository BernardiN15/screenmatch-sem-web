package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action"),
    COMEDIA("Comedy"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    CRIME("Crime");
    private String categoriaOmdb;
    Categoria(String categoriaOmdb){
        this.categoriaOmdb=categoriaOmdb;
    }

public static Categoria fromString(String texto){
        for(Categoria categoria:Categoria.values()){
        if(categoria.categoriaOmdb.equalsIgnoreCase(texto)){
            return categoria;
        }
        }
         throw new IllegalArgumentException("Nenhuma categoria foi encontrada!");
}
}
