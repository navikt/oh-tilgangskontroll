package no.nav.opppgavehandtering.tilgang.populasjon;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record PopulasjonstilgangResponse(Set<PopulasjonstilgangPerBrukerResultat> resultater) {
    public record PopulasjonstilgangPerBrukerResultat(
            @JsonProperty("brukerId") String personident,
            int status,
            @JsonProperty("title") String kode,
            Detaljer detaljer


    ) {
        public record Detaljer(@JsonProperty("title") String kode,  String begrunnelse){}
    }
}
