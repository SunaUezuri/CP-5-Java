package br.com.fiap.locadora.dto.cliente;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class DetalhesClienteDto {

    private int id;
    private String cpf;
    private String nome;
    private LocalDate dtNasc;
    private String endereco;
    private String email;
}
