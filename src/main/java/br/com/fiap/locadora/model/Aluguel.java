package br.com.fiap.locadora.model;

import java.time.LocalDate;

import lombok.*;

// Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Aluguel {

    private int id;
    private LocalDate dtAluguel;
    private LocalDate dtDevolucao;

    // Foreign Keys
    private Filme filme;
    private Cliente cliente;

    public Aluguel(int id, LocalDate dtAluguel, LocalDate dtDevolucao) {
        this.id = id;
        this.dtAluguel = dtAluguel;
        this.dtDevolucao = dtDevolucao;
    }


}
