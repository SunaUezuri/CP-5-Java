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

public class UpdateDaoTest {
    public static void main(String[] args) {

        Aluguel aluguel = new Aluguel(4, LocalDate.of(2024, 10, 16), LocalDate.of(2024, 10, 23));

        try {

            //Criando a conexão do banco de dados e os DAO's
            Connection conn = ConnectionFactory.getConnection();
            AluguelDao aluguelDao = new AluguelDao(conn);
            FilmeDao filmeDao = new FilmeDao(conn);
            ClienteDao clienteDao = new ClienteDao(conn);

            //Setando os id do filme e do cliente
            Filme filme = filmeDao.pesquisaId(21);
            aluguel.setFilme(filme);

            Cliente cliente = clienteDao.pesquisaId(4);
            aluguel.setCliente(cliente);

            //Executando a atualização
            aluguelDao.update(aluguel);
            System.out.println("Update realizado com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
