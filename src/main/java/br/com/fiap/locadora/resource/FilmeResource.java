package br.com.fiap.locadora.resource;


import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.dto.filme.AtualizaFilmeDto;
import br.com.fiap.locadora.dto.filme.AtualizaParcialDto;
import br.com.fiap.locadora.dto.filme.CadastroFilmeDto;
import br.com.fiap.locadora.dto.filme.DetalhesFilmeDto;
import br.com.fiap.locadora.exceptions.*;
import br.com.fiap.locadora.model.Filme;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/filme")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FilmeResource {

    private FilmeDao filmeDao;
    private ModelMapper modelMapper;

    public FilmeResource() throws Exception{
        filmeDao = new FilmeDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    //Método POST
    @POST
    public Response cadastro(@Valid CadastroFilmeDto dto, @Context UriInfo uriInfo) throws SQLException{
        Filme filme = modelMapper.map(dto, Filme.class);
        filmeDao.cadastrar(filme);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(filme.getId()));
        return Response.created(builder.build()).build();
    }

    //Método GET
    @GET
    public List<DetalhesFilmeDto> buscar() throws SQLException{
        return filmeDao.listar().stream()
                .map(f -> modelMapper.map(f, DetalhesFilmeDto.class))
                .collect(Collectors.toList());
    }

    //Método GET para utilizar com o Id
    @Path("/{id}")
    @GET
    public DetalhesFilmeDto buscarId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        Filme filme = filmeDao.pesquisaId(id);
        DetalhesFilmeDto dto = modelMapper.map(filme, DetalhesFilmeDto.class);
        return dto;
    }

    //Método para pesquisar pelo nome
    @Path("pesquisa/nomes")
    @GET
    public List<DetalhesFilmeDto> buscarNome(@DefaultValue("e") @QueryParam("nome") String nome) throws SQLException, NomeNaoEncontradoException {
        return filmeDao.pesquisaNome(nome).stream()
                .map(f -> modelMapper.map(f, DetalhesFilmeDto.class))
                .collect(Collectors.toList());
    }

    @Path("pesquisa/generos")
    @GET
    public List<DetalhesFilmeDto> buscarGenero(@DefaultValue("ACAO") @QueryParam("genero") String genero) throws SQLException, GeneroNaoEncontradoException {
        return filmeDao.pesquisaGenero(genero).stream()
                .map(f -> modelMapper.map(f, DetalhesFilmeDto.class))
                .collect(Collectors.toList());
    }

    @Path("pesquisa/diretores")
    @GET
    public List<DetalhesFilmeDto> buscarDiretor(@DefaultValue("a") @QueryParam("diretor") String diretor) throws SQLException, DiretorNaoEncontradoException {
        return filmeDao.pesquisaDiretor(diretor).stream()
                .map(f -> modelMapper.map(f, DetalhesFilmeDto.class))
                .collect(Collectors.toList());
    }

    @Path("pesquisa/ano/lancamento")
    @GET
    public List<DetalhesFilmeDto> buscarAno(@DefaultValue("1990") @QueryParam("ano") int ano) throws SQLException, AnoNaoEncontradoException {
        return filmeDao.pesquisaAnoLancamento(ano).stream()
                .map(f -> modelMapper.map(f, DetalhesFilmeDto.class))
                .collect(Collectors.toList());
    }

    //Método para dar update
    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizaFilmeDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Filme filme = modelMapper.map(dto, Filme.class);
        filme.setId(id);
        filmeDao.atualizar(filme);
        return Response.ok().build();
    }

    //Método para atualizar parcialmente
    @PATCH
    @Path("/{id}")
    public Response atualizaParcial(@Valid @PathParam("id") int id, AtualizaParcialDto dto) throws IdNaoEncontradoException, SQLException{
        //Pesquisar o cliente no banco
        Filme filme = filmeDao.pesquisaId(id);

        //Verificando se o valor existe, e injetar o valor novo
        if (dto.getNome() != null)
            filme.setNome(dto.getNome());
        if (dto.getDiretor() != null)
            filme.setDiretor(dto.getDiretor());
        if (dto.getAnoLancamento() != 0)
            filme.setAnoLancamento(dto.getAnoLancamento());
        if (dto.getGenero() != null)
            filme.setGenero(dto.getGenero());
        if (dto.getSinopse() != null)
            filme.setSinopse(dto.getSinopse());
        if (dto.getDuracao() != 0)
            filme.setDuracao(dto.getDuracao());
        if (dto.getClassificacao() != null)
            filme.setClassificacao(dto.getClassificacao());

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws IdNaoEncontradoException, SQLException{
        filmeDao.apagar(id);
        return Response.noContent().build();
    }

}
