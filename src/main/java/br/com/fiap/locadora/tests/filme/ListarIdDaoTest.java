package br.com.fiap.locadora.tests.filme;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.model.Filme;

import java.sql.Connection;

public class ListarIdDaoTest {
    public static void main(String[] args) {

        try {

            //Criando a conexão com o banco e a classe DAO
            Connection conn = ConnectionFactory.getConnection();
            FilmeDao dao = new FilmeDao(conn);

            Filme filme = dao.pesquisaId(17);

            //Exibindo os dados encontrados
            System.out.println(filme);

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
