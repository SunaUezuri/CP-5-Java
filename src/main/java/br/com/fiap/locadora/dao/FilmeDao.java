package br.com.fiap.locadora.dao;

import br.com.fiap.locadora.exceptions.*;
import br.com.fiap.locadora.model.Filme;
import br.com.fiap.locadora.model.Genero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmeDao {

    private static final String INSERT_SQL = "insert into t_filme " +
            "values (sq_t_filme.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SQL = "select * from t_filme order by id_filme";
    private static final String SELECT_ID_SQL = "select * from t_filme where id_filme = ?";
    private static final String SELECT_NOME_SQL = "select * from t_filme where upper(nm_filme) like upper(?) order by id_filme";
    private static final String SELECT_GENERO_SQL = "select * from t_filme where upper(ds_genero) like upper(?) order by id_filme";
    private static final String SELECT_DIRETOR_SQL = "select * from t_filme where upper(nm_diretor) like upper(?) order by id_filme";
    private static final String SELECT_ANO_SQL = "select * from t_filme where ano_lancamento = ? order by id_filme";
    private static final String DELETE_SQL = "delete from t_filme where id_filme = ?";
    private static final String UPDATE_SQL = "update t_filme set id_locadora = ?, nm_filme = ?, nm_diretor = ?, ano_lancamento = ?, ds_genero = ?, ds_sinopse = ?, tm_duracao = ?, ds_classificacao = ? where id_filme = ?";

    private Connection conexao;

    public FilmeDao(Connection conexao){
        this.conexao = conexao;
    }

    //Método para cadastrar um filme
    public void cadastrar(Filme filme) throws SQLException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"id_filme"});

        //Setando os valores
        preencherStatementComFilme(stmt, filme);

        //Executar o comando SQL
        stmt.executeUpdate();

        //Recuperando o id gerado pela sequence
        ResultSet resultSet = stmt.getGeneratedKeys();
        resultSet.next();
        filme.setId(resultSet.getInt(1));
    }

    //Método para listar todos os filmes
    public List<Filme> listar() throws SQLException{

        //Criando o Statement
        Statement stm = conexao.createStatement();

        //Executar o comand SQL
        ResultSet resultSet = stm.executeQuery(SELECT_SQL);

        //Criando a lista de Filmes
        List<Filme> lista = new ArrayList<>();

        //Salvando os registros encontrados em uma lista
        while (resultSet.next()){

            Filme filme = parseFilme(resultSet);
            lista.add(filme);

        }

        return lista;
    }

    //Listar o filme por ID
    public Filme pesquisaId(int id) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ID_SQL);

        //Setando o valor do ID na querry
        stmt.setInt(1, id);

        //Executar o comando SQL
        ResultSet resultSet = stmt.executeQuery();

        if (!resultSet.next()){
            throw new IdNaoEncontradoException("Filme não encontrado");
        }

        return parseFilme(resultSet);
    }

    public List<Filme> pesquisaNome(String nome) throws SQLException, NomeNaoEncontradoException {

        //Executando o comando SQL
        PreparedStatement stmt = conexao.prepareStatement(SELECT_NOME_SQL);

        //Setando o valor da querry
        stmt.setString(1, "%" + nome + "%");

        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista de filmes
        List<Filme> lista = new ArrayList<>();

        //Comando para percorrer a tabela e salvar os elementos encontrados
        while (resultSet.next()){

            Filme filme = parseFilme(resultSet);
            lista.add(filme);

        }

        //Comando para avisar se a lista está vazia
        if (lista.isEmpty()){
            throw new NomeNaoEncontradoException("Filme não encontrado");
        }

        return lista;
    }

    //Método de pesquisa por gênero
    public List<Filme> pesquisaGenero(String genero) throws SQLException, GeneroNaoEncontradoException{

        //Executando o comando SQL
        PreparedStatement stmt = conexao.prepareStatement(SELECT_GENERO_SQL);

        //Setando o valor da querry
        stmt.setString(1, "%" + genero + "%");

        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista de filmes
        List<Filme> lista = new ArrayList<>();

        //Comando para percorrer e salvar os registros
        while (resultSet.next()){
            Filme filme = parseFilme(resultSet);
            lista.add(filme);
        }

        if (lista.isEmpty()){
            throw new GeneroNaoEncontradoException("Filme não encontrado");
        }

        return lista;
    }

    //Método para pesquisar o filme por diretor
    public List<Filme> pesquisaDiretor(String diretor) throws SQLException, DiretorNaoEncontradoException{

        //Criando o statement para o comando SQL
        PreparedStatement stmt = conexao.prepareStatement(SELECT_DIRETOR_SQL);

        //Setando o valor
        stmt.setString(1, "%" + diretor + "%");

        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista para armazenar os dados encontrados na pesquisa
        List<Filme> lista = new ArrayList<>();

        //Comando para percorrer e armazenar os dados encontrados
        while (resultSet.next()){

            Filme filme = parseFilme(resultSet);
            lista.add(filme);
        }

        if (lista.isEmpty()){
            throw new DiretorNaoEncontradoException("Diretor não encontrado.");
        }

        return lista;
    }

    //Método para buscar o filme por ano de lançamento
    public List<Filme> pesquisaAnoLancamento(int ano) throws SQLException, AnoNaoEncontradoException{

        //Criando o Statement para o comando SQL
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ANO_SQL);

        //Setando os valores
        stmt.setInt(1, ano);

        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista de filmes e o comando para percorrer os registros e salvar eles como um filme na lista
        List<Filme> lista = new ArrayList<>();

        while (resultSet.next()){

            Filme filme = parseFilme(resultSet);
            lista.add(filme);
        }

        //If para caso não seja registrado nada na lista
        if (lista.isEmpty()){
            throw new AnoNaoEncontradoException("Não foi possível encontrar um filme lançado em: " + ano);
        }

        return lista;
    }

    //Método para deletar um filme por ID
    public void apagar(int id) throws SQLException, IdNaoEncontradoException{

        //Instanciando o PreparedStatement
        PreparedStatement stm = conexao.prepareStatement(DELETE_SQL);

        //Setando o valor do ID
        stm.setInt(1, id);

        //Executando o comando SQL caso exista o ID
        int linhas = stm.executeUpdate();

        if (linhas == 0){
            throw new IdNaoEncontradoException("Filme não encontrado");
        }
    }

    //Método para atualizar um filme
    public void atualizar(Filme filme) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL);

        //Setando os valores da querry
        preencherStatementComFilme(stmt, filme);

        stmt.setInt(9, filme.getId());

        //Executando o comando caso o ID exista
        int linhas = stmt.executeUpdate();

        if (linhas == 0){
            throw new IdNaoEncontradoException("Filme não encontrado.");
        }
    }

    private void preencherStatementComFilme(PreparedStatement stmt, Filme filme) throws SQLException{
        if (filme.getLocadora() != null){
            stmt.setInt(1, filme.getLocadora().getId());
        } else {
            stmt.setNull(1, Types.INTEGER);
        }
        stmt.setString(2, filme.getNome());
        stmt.setString(3, filme.getDiretor());
        stmt.setInt(4, filme.getAnoLancamento());
        if (filme.getGenero() != null) {
            stmt.setString(5, filme.getGenero().name());
        } else {
            stmt.setNull(5, Types.VARCHAR);
        }
        stmt.setString(6, filme.getSinopse());
        stmt.setInt(7, filme.getDuracao());
        stmt.setString(8, filme.getClassificacao());
    }

    private Filme parseFilme(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id_filme");
        String nome = resultSet.getString("nm_filme");
        String nomeDiretor = resultSet.getString("nm_diretor");
        int ano = resultSet.getInt("ano_lancamento");
        String generoStr = resultSet.getString("ds_genero");
        Genero genero = generoStr != null ? Genero.valueOf(generoStr) : null;
        String sinopse = resultSet.getString("ds_sinopse");
        int duracao = resultSet.getInt("tm_duracao");
        String classificacao = resultSet.getString("ds_classificacao");


        //Criando o objeto Filme
        Filme filme = new Filme(id, nome, nomeDiretor, ano, genero, sinopse, duracao, classificacao);

        return filme;
    }
}