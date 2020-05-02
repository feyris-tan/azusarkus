package moe.yo3explorer.azusa.notebook.boundary;

import moe.yo3explorer.azusa.notebook.entity.NoteEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/notebook/notes")
public class NoteResource {

    @Inject
    RestLicenseService licenseService;

    @GET
    @Tag(name = "notebook")
    @Produces(MediaType.APPLICATION_JSON)
    public List<NoteEntity> getNotes(@HeaderParam("Azusa-License") String lic)
    {
        licenseService.validateLicenseThrowing(lic);

        List<NoteEntity> notes = NoteEntity.findAll().list();
        return notes;
    }
}
