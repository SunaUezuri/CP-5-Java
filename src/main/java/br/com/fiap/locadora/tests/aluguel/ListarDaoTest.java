package br.com.fiap.locadora.tests.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;
import br.com.fiap.locadora.model.Aluguel;

import java.util.List;

public class ListarDaoTest {
    public static void main(String[] args) {

        try {

            //Criando o DAO e a conexão com o banco de dados
            AluguelDao dao = new AluguelDao(ConnectionFactory.getConnection());

            //Criando a lista de exibição e exibindo os resultados
            List<Aluguel> lista = dao.listar();

            for (Aluguel a : lista){
                System.out.println(a + "\n");
            }
            System.out.println("Aluguéis encontrados: " + lista.size());

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
