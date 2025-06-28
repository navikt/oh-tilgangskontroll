package no.nav.opppgavehandtering.tilgang.populasjon;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.jboss.resteasy.reactive.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static no.nav.opppgavehandtering.tilgang.populasjon.PopulasjonstilgangResultat.Kode.AVVIST_PERSON_UKJENT;

@ApplicationScoped
public class Populasjonstilgang {
    private static final Logger log = LoggerFactory.getLogger(Populasjonstilgang.class);
    private final TilgangsmaskinenRestClient tilgangsmaskinenRestClient;

    public Populasjonstilgang(@RestClient TilgangsmaskinenRestClient tilgangsmaskinenRestClient) {
        this.tilgangsmaskinenRestClient = tilgangsmaskinenRestClient;
    }


    public Map<String, PopulasjonstilgangResultat> kontroller(String navident, Set<PopulasjonstilgangRequest> foresporsler) {
        try {
            log.info("Kontrollerer populasjonstilgang for {} identer", foresporsler.size());
            PopulasjonstilgangResponse populasjonstilgangResponse = tilgangsmaskinenRestClient.kontrollerTilgang(navident, foresporsler);
            return populasjonstilgangResponse.resultater().stream().map(r -> switch (RestResponse.Status.fromStatusCode(r.status())) {
                case RestResponse.Status.NO_CONTENT -> new PopulasjonstilgangResultat(true, r.personident(), null);
                case RestResponse.Status.NOT_FOUND ->
                        new PopulasjonstilgangResultat(false, r.personident(), new PopulasjonstilgangResultat.TilgangAvvist(AVVIST_PERSON_UKJENT, "Kunne ikke identifisere person"));
                case RestResponse.Status.FORBIDDEN ->
                        new PopulasjonstilgangResultat(false, r.personident(), new PopulasjonstilgangResultat.TilgangAvvist(PopulasjonstilgangResultat.Kode.valueOf(r.detaljer().kode()), r.detaljer().begrunnelse()));
                default -> {
                    log.warn("Fikk ukjent statuskode fra tilgangsmaskinen: {}", r.status());
                    yield new PopulasjonstilgangResultat(false, r.personident(), new PopulasjonstilgangResultat.TilgangAvvist(PopulasjonstilgangResultat.Kode.AVVIST_UKJENT_AARSAK, "Ukjent svar fra tilgangsmaskinen"));
                }
            }).collect(Collectors.toMap(PopulasjonstilgangResultat::personident, Function.identity()));
        } catch (ClientWebApplicationException e) {
            String json = e.getResponse().readEntity(String.class);
            log.warn("Feilkode: {}, Kunne ikke kontrollere populasjonstilgang: {}", e.getResponse().getStatus(), json, e);
            throw new IllegalStateException(e);
        } catch (Exception e) {
            log.warn("Teknisk feil", e);
            throw new IllegalStateException(e);
        }
    }

}
