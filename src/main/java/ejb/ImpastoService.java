package ejb;

import dao.ImpastoDao;
import model.Impasto;
import DTO.ImpastoDTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/impasto")
public class ImpastoService {
//    private ImpastoDao impastoDao = new ImpastoDao();
	
	@EJB 
	private ImpastoDao impastoDao;

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
        return "Impasto creato";
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateImpasto(@PathParam("id") int id, ImpastoDTO impastoDTO) {
        impastoDTO.setId(id);
        Impasto impasto = convertToEntity(impastoDTO);
        impastoDao.updateImpasto(impasto);
        return "Impasto aggiornato";
    }

    @DELETE
    @Path("/{id}")
    public String deleteImpasto(@PathParam("id") int id) {
        impastoDao.deleteImpasto(id);
        return "Impasto cancellato"; 
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
