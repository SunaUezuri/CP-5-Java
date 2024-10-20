package br.com.fiap.locadora.tests.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;
import br.com.fiap.locadora.model.Aluguel;

import java.sql.Connection;
import java.util.List;

public class ListaIdClienteDaoTest {
    public static void main(String[] args) {

        try {

            //Criando o DAO e a conexão com o banco de dados
            Connection conn = ConnectionFactory.getConnection();
            AluguelDao dao = new AluguelDao(conn);

            //Instanciando a lista de exibição
            List<Aluguel> lista = dao.pesquisaIdCliente(4);

            //Exibindo os dados do aluguel
            for (Aluguel a : lista){
                System.out.println(a + "\n");
            }
            System.out.println("Aluguéis encontrados: " + lista.size());

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
