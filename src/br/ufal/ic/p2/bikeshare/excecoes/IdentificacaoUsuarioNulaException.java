package br.ufal.ic.p2.bikeshare.excecoes;

public class IdentificacaoUsuarioNulaException extends BikeShareException {
    public IdentificacaoUsuarioNulaException() {
        super("Identificacao do usuario nao pode ser nula.");
    }
}
