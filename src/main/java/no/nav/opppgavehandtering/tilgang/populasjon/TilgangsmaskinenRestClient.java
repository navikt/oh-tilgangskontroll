package no.nav.opppgavehandtering.tilgang.populasjon;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Set;

@RegisterRestClient(configKey = "tilgangsmaskinen")
//@Retry(maxRetries = 2)
//@Timeout(1000)
public interface TilgangsmaskinenRestClient {

    @POST
    @Produces("application/json")
    @Path("/{navident}")
    PopulasjonstilgangResponse kontrollerTilgang(String navident, Set<PopulasjonstilgangRequest> request);

}
