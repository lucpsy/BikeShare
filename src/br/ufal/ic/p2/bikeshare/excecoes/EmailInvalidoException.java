package br.ufal.ic.p2.bikeshare.excecoes;

public class EmailInvalidoException extends BikeShareException {
    public EmailInvalidoException() {
        super("Email invalido.");
    }
}
