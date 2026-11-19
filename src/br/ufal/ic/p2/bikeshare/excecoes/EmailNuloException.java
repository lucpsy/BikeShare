package br.ufal.ic.p2.bikeshare.excecoes;

public class EmailNuloException extends BikeShareException {
    public EmailNuloException() {
        super("Email nao pode ser nulo.");
    }
}
