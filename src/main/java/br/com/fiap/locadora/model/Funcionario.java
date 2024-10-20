package br.com.fiap.locadora.model;

import lombok.*;

import java.time.LocalDate;

// Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario extends Pessoa{

    private int anoAdmissao;

    //FK
    private Locadora locadora;

    @Override
    public String toString() {
        return super.toString() + anoAdmissao;
    }

    public Funcionario(int id, String cpf, String nome, LocalDate dtNasc, int anoAdmissao) {
        super(id, cpf, nome, dtNasc);
        this.anoAdmissao = anoAdmissao;
    }
}
