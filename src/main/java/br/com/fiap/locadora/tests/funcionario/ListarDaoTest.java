package br.com.fiap.locadora.tests.funcionario;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FuncionarioDao;
import br.com.fiap.locadora.model.Funcionario;

import java.sql.Connection;
import java.util.List;

public class ListarDaoTest {
    public static void main(String[] args) {

        try {

            //Criando a conexão com o banco e o DAO do funcionário
            Connection conn = ConnectionFactory.getConnection();
            FuncionarioDao dao = new FuncionarioDao(conn);

            //Criando a lista de exibição dos funcionários
            List<Funcionario> lista = dao.listar();

            for (Funcionario f : lista){
                System.out.println(f + "\n");
            }
            System.out.println("Funcionários encontrados: " + lista.size());

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
