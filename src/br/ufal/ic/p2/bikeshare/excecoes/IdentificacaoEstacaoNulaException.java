package br.ufal.ic.p2.bikeshare.excecoes;

public class IdentificacaoEstacaoNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public IdentificacaoEstacaoNulaException() {
        super("Identificacao da estacao nao pode ser nula.");
    }
}
