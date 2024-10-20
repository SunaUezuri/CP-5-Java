package br.com.fiap.locadora.resource;


import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;
import br.com.fiap.locadora.dto.aluguel.AtualizaAluguelDto;
import br.com.fiap.locadora.dto.aluguel.CadastroAluguelDto;
import br.com.fiap.locadora.dto.aluguel.DetalhesAluguelDto;
import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.model.Aluguel;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/aluguel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AluguelResource {

    private AluguelDao aluguelDao;
    private ModelMapper modelMapper;

    public AluguelResource() throws Exception{
        aluguelDao = new AluguelDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroAluguelDto dto, @Context UriInfo uriInfo) throws SQLException {
        Aluguel aluguel = modelMapper.map(dto, Aluguel.class);
        aluguelDao.cadastrar(aluguel);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(aluguel.getId()));
        return Response.created(builder.build()).build();
    }

    //Método GET
    @GET
    public List<DetalhesAluguelDto> buscar() throws SQLException{
        return aluguelDao.listar().stream()
                .map(a -> modelMapper.map(a, DetalhesAluguelDto.class))
                .collect(Collectors.toList());
    }

    //Método GET para utilizar com o Id
    @Path("/{id}")
    @GET
    public DetalhesAluguelDto buscarId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        Aluguel aluguel = aluguelDao.pesquisaId(id);
        DetalhesAluguelDto dto = modelMapper.map(aluguel, DetalhesAluguelDto.class);
        return dto;
    }

    //Método para dar update
    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizaAluguelDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Aluguel aluguel = modelMapper.map(dto, Aluguel.class);
        aluguel.setId(id);
        aluguelDao.update(aluguel);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws IdNaoEncontradoException, SQLException{
        aluguelDao.deletar(id);
        return Response.noContent().build();
    }
}
