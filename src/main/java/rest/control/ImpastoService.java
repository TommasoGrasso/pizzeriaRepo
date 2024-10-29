package rest.control;

import dao.ImpastoDao;
import model.Impasto;
import DTO.ImpastoDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/impasti")
public class ImpastoService {
    private ImpastoDao impastoDao = new ImpastoDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ImpastoDTO> getAllImpasti() {
        return impastoDao.getAllImpasti().stream()
                         .map(this::convertToDTO)
                         .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ImpastoDTO getImpastoById(@PathParam("id") int id) {
        Impasto impasto = impastoDao.getImpastoById(id);
        if (impasto == null) {
            throw new WebApplicationException(404);
        }
        return convertToDTO(impasto);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createImpasto(ImpastoDTO impastoDTO) {
        Impasto impasto = convertToEntity(impastoDTO);
        impastoDao.saveImpasto(impasto);
        return "Impasto created successfully";
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateImpasto(@PathParam("id") int id, ImpastoDTO impastoDTO) {
        impastoDTO.setId(id);
        Impasto impasto = convertToEntity(impastoDTO);
        impastoDao.updateImpasto(impasto);
        return "Impasto updated successfully";
    }

    @DELETE
    @Path("/{id}")
    public String deleteImpasto(@PathParam("id") int id) {
        impastoDao.deleteImpasto(id);
        return "Impasto deleted successfully";
    }

    private ImpastoDTO convertToDTO(Impasto impasto) {
        return new ImpastoDTO(impasto.getId(), impasto.getNome());
    }

    private Impasto convertToEntity(ImpastoDTO impastoDTO) {
        Impasto impasto = new Impasto();
        impasto.setId(impastoDTO.getId());
        impasto.setNome(impastoDTO.getNome());
        return impasto;
    }
}
