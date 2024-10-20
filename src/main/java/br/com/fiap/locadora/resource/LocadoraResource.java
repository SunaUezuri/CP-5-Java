package br.com.fiap.locadora.resource;


import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.dto.locadora.AtualizaLocadora;
import br.com.fiap.locadora.dto.locadora.AtualizaParcial;
import br.com.fiap.locadora.dto.locadora.CadastroLocadoraDto;
import br.com.fiap.locadora.dto.locadora.DetalhesLocadoraDto;
import br.com.fiap.locadora.exceptions.EnderecoNaoEncontradoException;
import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.exceptions.NomeNaoEncontradoException;
import br.com.fiap.locadora.model.Locadora;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/locadora")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocadoraResource {

    private LocadoraDao locadoraDao;
    private ModelMapper modelMapper;

    public LocadoraResource() throws Exception{
        locadoraDao = new LocadoraDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    //Método POST
    @POST
    public Response cadastrar(@Valid CadastroLocadoraDto dto, @Context UriInfo uriInfo) throws SQLException{
        Locadora locadora = modelMapper.map(dto, Locadora.class);
        locadoraDao.cadastrar(locadora);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(locadora.getId()));
        return  Response.created(builder.build()).build();
    }

    //Método GET
    @GET
    public List<DetalhesLocadoraDto> buscar() throws SQLException{
        return locadoraDao.pesquisar().stream()
                .map(l -> modelMapper.map(l, DetalhesLocadoraDto.class))
                .collect(Collectors.toList());
    }

    //Pesquisar locadora por id
    @Path("/{id}")
    @GET
    public DetalhesLocadoraDto buscarId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        Locadora locadora = locadoraDao.pesquisaId(id);
        DetalhesLocadoraDto dto = modelMapper.map(locadora, DetalhesLocadoraDto.class);
        return dto;
    }

    @Path("pesquisa")
    @GET
    public List<DetalhesLocadoraDto> buscarNome(@DefaultValue("a") @QueryParam("nome") String nome) throws SQLException, NomeNaoEncontradoException {
        return locadoraDao.pesquisarNome(nome).stream()
                .map(l -> modelMapper.map(l, DetalhesLocadoraDto.class))
                .collect(Collectors.toList());
    }

    @Path("pesquisa/enderecos")
    @GET
    public List<DetalhesLocadoraDto> buscarEndereco(@DefaultValue("e") @QueryParam("endereco") String endereco) throws SQLException, EnderecoNaoEncontradoException {
        return locadoraDao.pesquisaEndereco(endereco).stream()
                .map(l -> modelMapper.map(l, DetalhesLocadoraDto.class))
                .collect(Collectors.toList());
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizaLocadora dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Locadora locadora = modelMapper.map(dto, Locadora.class);
        locadora.setId(id);
        locadoraDao.atualizar(locadora);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        locadoraDao.apagar(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}")
    public Response atualizaParcial(@PathParam("id") int id, AtualizaParcial dto) throws SQLException, IdNaoEncontradoException{
        Locadora locadora = locadoraDao.pesquisaId(id);

        if (dto.getNome() != null)
            locadora.setNome(dto.getNome());
        if (dto.getEndereco() != null)
            locadora.setEndereco(dto.getEndereco());
        if (dto.getTelefone() != null)
            locadora.setTelefone(dto.getTelefone());

        return Response.ok().build();
    }
}
