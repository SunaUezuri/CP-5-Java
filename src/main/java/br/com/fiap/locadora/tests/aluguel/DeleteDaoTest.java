package br.com.fiap.locadora.tests.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;

import java.sql.Connection;

public class DeleteDaoTest {
    public static void main(String[] args) {

        try {

            //Criando a conexão do banco e o dao
            Connection conn = ConnectionFactory.getConnection();
            AluguelDao dao = new AluguelDao(conn);

            //Executando o delete
            dao.deletar(1);
            System.out.println("Filme deletado com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
