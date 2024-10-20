package br.com.fiap.locadora.tests.funcionario;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FuncionarioDao;

public class DeleteDaoTest {
    public static void main(String[] args) {

        try {

            //Setando o DAO e a conexão com o banco de dados
            FuncionarioDao dao = new FuncionarioDao(ConnectionFactory.getConnection());

            dao.apagar(2);
            System.out.println("Funcionário deletado com sucesso!");
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
