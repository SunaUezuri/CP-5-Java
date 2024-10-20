package br.com.fiap.locadora.tests.filme;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Filme;
import br.com.fiap.locadora.model.Genero;
import br.com.fiap.locadora.model.Locadora;

public class CadastroDaoTest {
    public static void main(String[] args) {

        Filme filme = new Filme(0, "Matrix", "Raphael", 1990, Genero.ACAO,
                "Blue Pill or Red pill?", 150, "+18");

        try {

            //Setando a FK
            LocadoraDao locadoraDao = new LocadoraDao(ConnectionFactory.getConnection());
            Locadora locadora = locadoraDao.pesquisaId(2);
            filme.setLocadora(locadora);

            FilmeDao dao = new FilmeDao(ConnectionFactory.getConnection());

            dao.cadastrar(filme);
            System.out.println("Filme cadastrado com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
