package br.com.fiap.locadora.dto.locadora;

import jakarta.validation.constraints.NotBlank;

public class AtualizaLocadora {

    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotBlank
    private String telefone;
}
