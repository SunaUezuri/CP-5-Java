package br.com.fiap.locadora.model;

import lombok.*;

import java.time.LocalDate;

// Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Pessoa{

    //Atributos da classe Cliente
    private String endereco;
    private String email;

    public Cliente(int id, String cpf, String nome, LocalDate dtNasc, String endereco, String email) {
        super(id, cpf, nome, dtNasc);
        this.endereco = endereco;
        this.email = email;
    }
}
