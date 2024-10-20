package br.com.fiap.locadora.dto.locadora;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CadastroLocadoraDto {

    @NotBlank
    private String nome;
    private String endereco;
    private String telefone;
}
