package br.com.fiap.locadora.tests.cliente;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.model.Cliente;

import java.sql.Connection;
import java.util.List;

public class ListarDaoTest {
    public static void main(String[] args) {

        try {

            Connection conn = ConnectionFactory.getConnection();
            ClienteDao dao = new ClienteDao(conn);

            List<Cliente> clientes = dao.listar();

            System.out.println("Clientes encontrados: " + clientes.size());
            for (Cliente cliente : clientes){
                System.out.println(cliente);
            }

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
