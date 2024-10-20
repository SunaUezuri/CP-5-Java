package br.com.fiap.locadora.dto.filme;

import br.com.fiap.locadora.model.Genero;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetalhesFilmeDto {

    private int id;
    private String nome;
    private String diretor;
    private int anoLancamento;
    private Genero genero;
    private String sinopse;
    private int duracao;
    private String classificacao;
}
