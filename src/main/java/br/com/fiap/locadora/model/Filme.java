package br.com.fiap.locadora.model;


import lombok.*;

// Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Filme {

    private int id;
    private String nome;
    private String diretor;
    private int anoLancamento;
    private Genero genero;
    private String sinopse;
    private int duracao;
    private String classificacao;

    // Foreign Key
    private Locadora locadora;


    public Filme(int id, String nome, String diretor, int anoLancamento, Genero genero, String sinopse, int duracao, String classificacao) {
        this.id = id;
        this.nome = nome;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
        this.sinopse = sinopse;
        this.duracao = duracao;
        this.classificacao = classificacao;
    }
}
