package br.com.fiap.locadora.dto.aluguel;

import br.com.fiap.locadora.model.Cliente;
import br.com.fiap.locadora.model.Filme;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter @Getter
public class DetalhesAluguelDto {

    private int id;
    private LocalDate dtAluguel;
    private LocalDate dtDevolucao;

    // Foreign Keys
    private Filme filme;
    private Cliente cliente;
}
