package br.com.fiap.locadora.tests.filme;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.model.Filme;
import br.com.fiap.locadora.model.Genero;

import java.sql.Connection;
import java.util.List;

public class ListarGeneroDaoTest {
    public static void main(String[] args) {

        try {

            //Criando a conexão e o dao
            Connection conn = ConnectionFactory.getConnection();
            FilmeDao dao = new FilmeDao(conn);

            //Criando a lista de exibição
            List<Filme> lista = dao.pesquisaGenero("DRAMA");

            for (Filme f : lista){
                System.out.println(f + "\n");
            }
            System.out.println("Filmes encontrados: " + lista.size());

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
