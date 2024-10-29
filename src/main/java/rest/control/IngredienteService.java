package rest.control;

import dao.IngredienteDao;
import model.Ingrediente;
import DTO.IngredienteDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/ingredienti")
public class IngredienteService {
    private IngredienteDao ingredienteDao = new IngredienteDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<IngredienteDTO> getAllIngredienti() {
        return ingredienteDao.getAllIngrediente().stream()
                             .map(this::convertToDTO)
                             .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public IngredienteDTO getIngredienteById(@PathParam("id") int id) {
        Ingrediente ingrediente = ingredienteDao.getIngredienteById(id);
        if (ingrediente == null) {
            throw new WebApplicationException(404);
        }
        return convertToDTO(ingrediente);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createIngrediente(IngredienteDTO ingredienteDTO) {
        Ingrediente ingrediente = convertToEntity(ingredienteDTO);
        ingredienteDao.saveIngrediente(ingrediente);
        return "Ingrediente Creato";
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateIngrediente(@PathParam("id") int id, IngredienteDTO ingredienteDTO) {
        ingredienteDTO.setId(id);
        Ingrediente ingrediente = convertToEntity(ingredienteDTO);
        ingredienteDao.updateIngrediente(ingrediente);
        return "Ingrediente Aggiornato";
    }

    @DELETE
    @Path("/{id}")
    public String deleteIngrediente(@PathParam("id") int id) {
        ingredienteDao.deleteIngrediente(id);
        return "Ingrediente Cancellato";
    }

    private IngredienteDTO convertToDTO(Ingrediente ingrediente) {
        return new IngredienteDTO(ingrediente.getId(), ingrediente.getNome());
    }

    private Ingrediente convertToEntity(IngredienteDTO ingredienteDTO) {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(ingredienteDTO.getId());
        ingrediente.setNome(ingredienteDTO.getNome());
        return ingrediente;
    }
}
