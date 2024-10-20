package br.com.fiap.locadora.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class AtualizaParcial {

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
