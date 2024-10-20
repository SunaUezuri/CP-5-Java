package br.com.fiap.locadora.model;

import lombok.*;

import java.time.LocalDate;

//Utilizando Lombok para evitar código repetido
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Pessoa {

    //Criando os atributos da classe
    private int id;
    private String cpf;
    private String nome;
    private LocalDate dtNasc;

}
