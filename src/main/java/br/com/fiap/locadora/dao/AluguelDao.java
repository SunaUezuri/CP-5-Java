package br.com.fiap.locadora.dao;

import br.com.fiap.locadora.exceptions.DataNaoEncontradaException;
import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.model.Aluguel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AluguelDao {

    private static final String INSERT_SQL = "insert into t_aluguel values " +
            "(sq_t_aluguel.nextval, ?, ?, ?, ?)";
    private static final String SELECT_SQL = "select * from t_aluguel order by id_aluguel";
    private static final String SELECT_ID_SQL = "select * from t_aluguel where id_aluguel = ?";
    private static final String SELECT_ID_CLIENTE_SQL = "select * from t_aluguel where id_cliente = ?";
    private static final String SELECT_ID_FILME_SQL = "select * from t_aluguel where id_filme = ?";
    private static final String SELECT_DT_DEVOLUCAO_SQL = "select * from t_aluguel where dt_devolucao = ?";
    private static final String UPDATE_SQL = "update t_aluguel set id_filme = ?, id_cliente = ?, " +
            "dt_aluguel = ?, dt_devolucao = ? where id_aluguel = ?";
    private static final String DELETE_SQL = "delete from t_aluguel where id_aluguel = ?";

    private Connection conexao;

    public AluguelDao(Connection conexao){this.conexao = conexao;}

    //Método para cadastrar um aluguel e relacionar um filme e um cliente
    public void cadastrar(Aluguel aluguel) throws SQLException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"id_aluguel"});

        preencherStatementComAluguel(stmt, aluguel);

        stmt.executeUpdate();

        ResultSet resultSet = stmt.getGeneratedKeys();
        resultSet.next();
        aluguel.setId(resultSet.getInt(1));
    }

    public List<Aluguel> listar() throws SQLException{

        //Criando o Statement
        Statement stm = conexao.createStatement();

        //Executando o comando SQL
        ResultSet resultSet = stm.executeQuery(SELECT_SQL);

        //Criando a lista de aluguéis
        List<Aluguel> lista = new ArrayList<>();

        while (resultSet.next()){
            Aluguel aluguel = parseAluguel(resultSet);
            lista.add(aluguel);
        }

        return lista;
    }

    //Recuperar um aluguel pelo ID
    public Aluguel pesquisaId(int id) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ID_SQL);

        //Setando o valor na querry
        stmt.setInt(1, id);

        //Executar o comando SQL
        ResultSet resultSet = stmt.executeQuery();

        //Lançando uma exceção caso não encontre o ID
        if (!resultSet.next()){
            throw new IdNaoEncontradoException("Aluguel não encontrado.");
        }

        return parseAluguel(resultSet);
    }

    //Pesquisando um aluguel pelo ID do cliente
    public List<Aluguel> pesquisaIdCliente(int idCliente) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ID_CLIENTE_SQL);

        //Setando o valor na querry
        stmt.setInt(1, idCliente);

        //Executar o comando SQL
        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista de exibição
        List<Aluguel> lista = new ArrayList<>();

        while (resultSet.next()){
            Aluguel aluguel = parseAluguel(resultSet);
            lista.add(aluguel);
        }

        //Lançando uma exceção caso não encontre o ID
        if (lista.isEmpty()){
            throw new IdNaoEncontradoException("Aluguel não encontrado, ou este cliente " +
                    "não possui aluguéis no momento.");
        }

        return lista;
    }

    //Pesquisando um aluguel pelo ID do filme
    public List<Aluguel> pesquisaIdFilme(int idFilme) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ID_FILME_SQL);

        //Setando o valor na querry
        stmt.setInt(1, idFilme);

        //Executar o comando SQL
        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista de exibição
        List<Aluguel> lista = new ArrayList<>();

        while (resultSet.next()){
            Aluguel aluguel = parseAluguel(resultSet);
            lista.add(aluguel);
        }

        //Lançando uma exceção caso não encontre o ID
        if (lista.isEmpty()){
            throw new IdNaoEncontradoException("Aluguel não encontrado, ou este cliente " +
                    "não possui aluguéis no momento.");
        }

        return lista;
    }

    //Pesquisar os aluguéis pela Data de devolução
    public List<Aluguel> pesquisaDtDevolucao(LocalDate data) throws SQLException, DataNaoEncontradaException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(SELECT_DT_DEVOLUCAO_SQL);

        //Setando o valor na querry
        stmt.setDate(1, Date.valueOf(data));

        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista para salvar os aluguéis
        List<Aluguel> lista = new ArrayList<>();

        //Salvando os dados em um objeto da classe aluguel
        while (resultSet.next()){
            Aluguel aluguel = parseAluguel(resultSet);
            lista.add(aluguel);
        }

        if (lista.isEmpty()){
            throw new DataNaoEncontradaException("Nenhum aluguel está pendente para esta data.");
        }

        return lista;
    }

    //Método para dar update em um aluguel
    public void update(Aluguel aluguel) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL);

        //Setando os valores na querry
        preencherStatementComAluguel(stmt, aluguel);

        stmt.setInt(5, aluguel.getId());

        //Executando o comando SQL caso o ID seja encontrado

        int linhas = stmt.executeUpdate();

        if (linhas == 0){
            throw new IdNaoEncontradoException("Aluguel não encontrado.");
        }
    }

    //Método para deletar um aluguel
    public void deletar(int id) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatemente
        PreparedStatement stmt = conexao.prepareStatement(DELETE_SQL);

        //Setando o valor na querry
        stmt.setInt(1, id);

        //Executando o comando SQL caso o id seja válido
        int linhas = stmt.executeUpdate();

        if (linhas == 0){
            throw new IdNaoEncontradoException("Aluguel não encontrado.");
        }
    }

    private void preencherStatementComAluguel(PreparedStatement stmt, Aluguel aluguel) throws SQLException{
        //Configurando os valores na querry
        if (aluguel.getFilme() != null){
            stmt.setInt(1, aluguel.getFilme().getId());
        } else {
            stmt.setNull(2, Types.VARCHAR);
        }

        if (aluguel.getCliente() != null){
            stmt.setInt(2, aluguel.getCliente().getId());
        } else {
            stmt.setNull(2, Types.VARCHAR);
        }

        stmt.setDate(3, Date.valueOf(aluguel.getDtAluguel()));

        if (aluguel.getDtDevolucao() != null){
            stmt.setDate(4, Date.valueOf(aluguel.getDtDevolucao()));
        } else {
            stmt.setNull(4, Types.VARCHAR);
        }
    }

    private Aluguel parseAluguel(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id_aluguel");
        LocalDate dtAluguel = resultSet.getDate("dt_aluguel").toLocalDate();
        LocalDate dtDevolucao = resultSet.getDate("dt_devolucao").toLocalDate();

        //Criando o objeto do aluguel
        Aluguel aluguel = new Aluguel(id, dtAluguel, dtDevolucao);

        return aluguel;
    }
}