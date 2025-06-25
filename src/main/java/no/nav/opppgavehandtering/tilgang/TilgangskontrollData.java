package no.nav.opppgavehandtering.tilgang;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TilgangskontrollData(String id, String personident, String temakode, String enhetsnr) {
}
