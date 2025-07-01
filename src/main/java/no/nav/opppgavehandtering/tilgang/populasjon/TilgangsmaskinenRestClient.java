package no.nav.opppgavehandtering.tilgang.populasjon;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Set;

@RegisterRestClient(configKey = "tilgangsmaskinen")
@OidcClientFilter("tilgangsmaskinen-ccf")
public interface TilgangsmaskinenRestClient {

    @POST
    @Produces("application/json")
    @Path("/api/v1/bulk/ccf{navident}")
    PopulasjonstilgangResponse kontrollerTilgang(String navident, Set<PopulasjonstilgangRequest> request);

}
