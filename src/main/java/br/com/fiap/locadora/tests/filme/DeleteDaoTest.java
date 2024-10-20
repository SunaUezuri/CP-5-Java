package br.com.fiap.locadora.tests.filme;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;

import java.sql.Connection;

public class DeleteDaoTest {
    public static void main(String[] args) {

        try {

            //Criando a conexão do banco e o dao
            Connection conn = ConnectionFactory.getConnection();
            FilmeDao dao = new FilmeDao(conn);

            //Executando o delete
            dao.apagar(17);
            System.out.println("Filme deletado com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
