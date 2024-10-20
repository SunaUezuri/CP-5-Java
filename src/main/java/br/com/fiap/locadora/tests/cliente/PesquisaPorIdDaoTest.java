package br.com.fiap.locadora.tests.cliente;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.model.Cliente;

public class PesquisaPorIdDaoTest {
    public static void main(String[] args) {

        try {

            ClienteDao dao = new ClienteDao(ConnectionFactory.getConnection());
            Cliente cliente = dao.pesquisaId(3);
            System.out.println("Cliente pesquisado: " + cliente);

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
