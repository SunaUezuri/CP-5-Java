package br.com.fiap.locadora.dto.cliente;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter @Getter
public class CadastroClienteDto {

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @Size(max = 50)
    private String nome;

    @Past
    private LocalDate dtNasc;

    private String endereco;

    @Email
    private String email;
}
