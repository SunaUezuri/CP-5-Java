package br.com.fiap.locadora.tests.cliente;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.model.Cliente;

import java.time.LocalDate;

public class UpdateDaoTest {
    public static void main(String[] args) {

        try {

            ClienteDao dao = new ClienteDao(ConnectionFactory.getConnection());

            Cliente cliente = new Cliente(3, "19878645895", "Marinna Fernandez", LocalDate.of(1984, 5, 31),
                    "Rua Sebastião Dias Fragoso", "mr@gmail.com");

            dao.atualizar(cliente);
            System.out.println("Cliente atualizado com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
