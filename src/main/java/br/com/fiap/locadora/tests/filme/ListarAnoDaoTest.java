package br.com.fiap.locadora.tests.filme;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.model.Filme;

import java.sql.Connection;
import java.util.List;

public class ListarAnoDaoTest {
    public static void main(String[] args) {

        try {

            //Criando a conexão com o banco e o DAO
            Connection conn = ConnectionFactory.getConnection();
            FilmeDao dao = new FilmeDao(conn);

            //Criando a lista de exibição e exibindo os dados encontrados
            List<Filme> lista = dao.pesquisaAnoLancamento(1990);

            for (Filme f : lista){
                System.out.println(f + "\n");
            }
            System.out.println("Filmes encontrados: " + lista.size());

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
