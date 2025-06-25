package no.nav.opppgavehandtering.tilgang;

import io.smallrye.common.constraint.NotNull;

import java.util.Set;

public record TilgangskontrollRequest(@NotNull  String navident, @NotNull  Set<TilgangskontrollData> tilgangskontrollDataSet){}
