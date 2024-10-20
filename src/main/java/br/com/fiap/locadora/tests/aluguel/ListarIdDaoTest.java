package br.com.fiap.locadora.tests.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;
import br.com.fiap.locadora.model.Aluguel;

import java.sql.Connection;

public class ListarIdDaoTest {
    public static void main(String[] args) {

        try {

            //Criando o DAO e a conexão com o banco de dados
            Connection conn = ConnectionFactory.getConnection();
            AluguelDao dao = new AluguelDao(conn);

            Aluguel aluguel = dao.pesquisaId(1);

            //Exibindo os dados do aluguel
            System.out.println(aluguel);

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
