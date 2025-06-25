package no.nav.opppgavehandtering.tilgang;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import no.nav.opppgavehandtering.tilgang.populasjon.Populasjonstilgang;
import no.nav.opppgavehandtering.tilgang.populasjon.PopulasjonstilgangRequest;
import no.nav.opppgavehandtering.tilgang.populasjon.PopulasjonstilgangResultat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Path("api/tilgangskontroll")
public class TilgangskontrollApi {
    private static final Logger log = LoggerFactory.getLogger(TilgangskontrollApi.class);
    private final Populasjonstilgang populasjonstilgang;

    public TilgangskontrollApi(Populasjonstilgang populasjonstilgang) {
        this.populasjonstilgang = populasjonstilgang;
    }

    @POST
    @RunOnVirtualThread
    public Set<TilgangskontrollResultat> kontrollerTilgang(TilgangskontrollRequest request) {
        log.info("Kontrollerer tilgang for: {}", request.navident());
        Set<PopulasjonstilgangRequest> populasjonstilgangForesporsler = request.tilgangskontrollDataSet().stream().map(s -> new PopulasjonstilgangRequest(s.personident())).collect(Collectors.toSet());
        Map<String, PopulasjonstilgangResultat> populasjonstilgangResultat = populasjonstilgang.kontroller(request.navident(), populasjonstilgangForesporsler);
        return request.tilgangskontrollDataSet().stream().map(input -> {
            PopulasjonstilgangResultat resultatForPersonident = populasjonstilgangResultat.get(input.personident());
            return new TilgangskontrollResultat(input, resultatForPersonident.harTilgang(),
                    resultatForPersonident.harTilgang() ? null : TilgangskontrollResultat.Kode.valueOf(resultatForPersonident.tilgangAvvist().kode().name()),
                    resultatForPersonident.harTilgang() ? null : resultatForPersonident.tilgangAvvist().begrunnelse());
        }).collect(Collectors.toSet());
        //
//        log.info("Mappet");
//        Tuple2<Set<PopulasjonstilgangResultat>, Set<PopulasjonstilgangResultat>> tuple = Uni.combine().all().unis(
//                        populasjonstilgang.kontrollerReactive(request.navident(),
//                                populasjonstilgangForesporsler),
//                        populasjonstilgang.kontrollerReactive(request.navident(),
//                                populasjonstilgangForesporsler))
//                .asTuple()
//                .await().indefinitely();

//        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//            StructuredTaskScope.Subtask<Set<PopulasjonstilgangResultat>> resultat = scope.fork(() -> populasjonstilgang.kontroller(request.navident(),
//                    identer));
//            StructuredTaskScope.Subtask<Set<PopulasjonstilgangResultat>> resultat2 = scope.fork(() -> populasjonstilgang.kontroller(request.navident(),
//                    identer));
//            scope.join();
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        return new ArrayList<>();
    }
}
