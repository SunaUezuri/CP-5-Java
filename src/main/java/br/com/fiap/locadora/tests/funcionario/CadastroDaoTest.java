package br.com.fiap.locadora.tests.funcionario;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FuncionarioDao;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Funcionario;
import br.com.fiap.locadora.model.Locadora;

import java.sql.Connection;
import java.time.LocalDate;

public class CadastroDaoTest {
    public static void main(String[] args) {

        try {
        //Criando a conexão com o banco e o DAO do Funcionário
        Connection conn = ConnectionFactory.getConnection();
        FuncionarioDao dao = new FuncionarioDao(conn);

        Funcionario funcionario = new Funcionario(0, "06698457405", "Natalia Souza",
                LocalDate.of(1999, 12, 18), 2021);

        LocadoraDao locaDao = new LocadoraDao(conn);
        Locadora locadora = locaDao.pesquisaId(2);
        funcionario.setLocadora(locadora);


        dao.cadastrar(funcionario);
        System.out.println("Funcionário " + funcionario.getNome() + " cadastrado com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
    }
    }
}
