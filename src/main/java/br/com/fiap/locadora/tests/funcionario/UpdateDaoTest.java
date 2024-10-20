package br.com.fiap.locadora.tests.funcionario;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FuncionarioDao;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Funcionario;
import br.com.fiap.locadora.model.Locadora;

import java.sql.Connection;
import java.time.LocalDate;

public class UpdateDaoTest {
    public static void main(String[] args) {

        Funcionario funcionario = new Funcionario(1, "15486745328", "Marco Antonio",
                LocalDate.of(2005, 1, 16), 2024);

        try {

            //Criando a conexão com o banco e o DAO do Fncionário e da Locadora para setar a FK
            Connection conn = ConnectionFactory.getConnection();
            FuncionarioDao daoFunc = new FuncionarioDao(conn);

            LocadoraDao locaDao = new LocadoraDao(conn);
            Locadora locadora = locaDao.pesquisaId(2);
            funcionario.setLocadora(locadora);

            daoFunc.atualizar(funcionario);
            System.out.println("Funcionário atualizado com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
