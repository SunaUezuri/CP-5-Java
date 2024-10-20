package br.com.fiap.locadora.tests.funcionario;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FuncionarioDao;
import br.com.fiap.locadora.model.Funcionario;

import java.sql.Connection;

public class ListarIdDaoTest {
    public static void main(String[] args) {

        try {

            //Criando a conexão com o banco e o DAO da locadora
            Connection conn = ConnectionFactory.getConnection();
            FuncionarioDao dao = new FuncionarioDao(conn);

            Funcionario funcionario = dao.pesquisarId(4);

            //Exibindo os dados pesquisados
            System.out.println(funcionario);

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
