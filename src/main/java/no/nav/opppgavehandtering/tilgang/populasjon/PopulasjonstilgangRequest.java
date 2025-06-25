package no.nav.opppgavehandtering.tilgang.populasjon;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PopulasjonstilgangRequest(@JsonProperty("brukerId") String personident) {
}
