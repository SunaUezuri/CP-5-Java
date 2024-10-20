package br.com.fiap.locadora.tests.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;
import br.com.fiap.locadora.model.Aluguel;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class ListaDevolucaoDaoTest {
    public static void main(String[] args) {

        try {

            //Criando a conexão com o banco de dados e o DAO do aluguel
            Connection conn = ConnectionFactory.getConnection();
            AluguelDao dao = new AluguelDao(conn);

            //Criando a lista de exibição
            List<Aluguel> lista = dao.pesquisaDtDevolucao(LocalDate.of(2024, 10, 24));

            for (Aluguel a : lista){
                System.out.println(a + "\n");
            }
            System.out.println("Aluguéis encontrados: " + lista.size());

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
