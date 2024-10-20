package br.com.fiap.locadora.tests.filme;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Filme;
import br.com.fiap.locadora.model.Genero;
import br.com.fiap.locadora.model.Locadora;

import java.sql.Connection;

public class UpdateDaoTest {
    public static void main(String[] args) {

        Filme filme = new Filme(19, "Matrix", "Raphael", 1998, Genero.ACAO,
                "Pilula azul ou vermelha?", 158, "+16");

        try {

            //Instanciar o DAO
            Connection conn = ConnectionFactory.getConnection();
            FilmeDao dao = new FilmeDao(conn);

            LocadoraDao daoLoca = new LocadoraDao(conn);
            Locadora locadora = daoLoca.pesquisaId(4);
            filme.setLocadora(locadora);

            dao.atualizar(filme);
            System.out.println("Update realizado com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
