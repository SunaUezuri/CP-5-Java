package br.com.fiap.locadora.tests.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.model.Aluguel;
import br.com.fiap.locadora.model.Cliente;
import br.com.fiap.locadora.model.Filme;

import java.sql.Connection;
import java.time.LocalDate;

public class CadastroDaoTest {
    public static void main(String[] args) {

        Aluguel aluguel = new Aluguel(0, LocalDate.of(2024, 10, 17), LocalDate.of(2024, 10, 24));

        try {

            //Criando o DAO e a conexão com o banco de dados
            Connection conn = ConnectionFactory.getConnection();
            AluguelDao aluguelDao = new AluguelDao(conn);

            //Pesquisando e setando a FK id_cliente
            ClienteDao clienteDao = new ClienteDao(conn);
            Cliente cliente = clienteDao.pesquisaId(3);
            aluguel.setCliente(cliente);

            //Pesquisando e setando a FK id_filme
            FilmeDao filmeDao = new FilmeDao(conn);
            Filme filme = filmeDao.pesquisaId(21);
            aluguel.setFilme(filme);

            //Executando o cadastro
            aluguelDao.cadastrar(aluguel);
            System.out.println("Filme " + aluguel.getFilme().getNome() +
                    " alugado com sucesso por: " + aluguel.getCliente().getNome());

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
