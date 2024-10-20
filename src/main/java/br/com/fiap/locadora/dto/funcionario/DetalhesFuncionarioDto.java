package br.com.fiap.locadora.dto.funcionario;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class DetalhesFuncionarioDto {

    private int id;
    private String cpf;
    private String nome;
    private LocalDate dtNasc;
    private int anoAdmissao;
}
