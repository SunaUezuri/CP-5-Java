package br.com.fiap.locadora.dto.filme;

import br.com.fiap.locadora.model.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CadastroFilmeDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String diretor;
    @NotNull @Positive
    private int anoLancamento;
    private Genero genero;
    @NotBlank
    private String sinopse;
    @NotNull @Positive
    private int duracao;
    @NotBlank
    private String classificacao;
}
