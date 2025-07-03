package no.nav.opppgavehandtering.tilgang;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TilgangskontrollResultat(Long id, boolean harTilgang, Kode kode, String begrunnelse) {
    public enum Kode {
        AVVIST_STRENGT_FORTROLIG_ADRESSE,
        AVVIST_STRENGT_FORTROLIG_UTLAND,
        AVVIST_AVDØD,
        AVVIST_PERSON_UTLAND,
        AVVIST_SKJERMING,
        AVVIST_FORTROLIG_ADRESSE,
        AVVIST_PERSON_UKJENT,
        AVVIST_GEOGRAFISK,
        AVVIST_HABILITET,
        AVVSIT_TEMA,
        AVVIST_ENHET,
        AVVIST_UKJENT_AARSAK
    }
}
