package no.nav.opppgavehandtering.tilgang.populasjon;

public record PopulasjonstilgangResultat(boolean harTilgang, String personident, TilgangAvvist tilgangAvvist) {
    public record TilgangAvvist(Kode kode, String begrunnelse) {}

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
        AVVIST_UKJENT_AARSAK
    }
}
