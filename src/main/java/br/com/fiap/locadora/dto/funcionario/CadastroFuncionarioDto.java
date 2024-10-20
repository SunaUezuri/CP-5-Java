package br.com.fiap.locadora.dto.funcionario;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CadastroFuncionarioDto {

    @NotBlank
    private String cpf;
    @NotBlank
    private String nome;
    @Past
    private LocalDate dtNasc;
    @NotNull
    private int anoAdmissao;
}
