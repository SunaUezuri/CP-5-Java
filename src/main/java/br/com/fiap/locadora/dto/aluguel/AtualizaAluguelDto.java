package br.com.fiap.locadora.dto.aluguel;

import br.com.fiap.locadora.model.Cliente;
import br.com.fiap.locadora.model.Filme;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter @Getter
public class AtualizaAluguelDto {

    @Past
    private LocalDate dtAluguel;
    @Future
    private LocalDate dtDevolucao;

    // Foreign Keys
    private Filme filme;
    private Cliente cliente;
}
