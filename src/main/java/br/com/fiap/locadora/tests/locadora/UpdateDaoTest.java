package br.com.fiap.locadora.tests.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Locadora;

public class UpdateDaoTest {
    public static void main(String[] args) {

        try {

            LocadoraDao dao = new LocadoraDao(ConnectionFactory.getConnection());

            Locadora locadora = new Locadora(4, "Ultra mega Loca", "Jardim Angela", "");

            dao.atualizar(locadora);

            System.out.println("Update realizado com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
