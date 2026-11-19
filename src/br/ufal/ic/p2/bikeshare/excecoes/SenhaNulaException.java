package br.ufal.ic.p2.bikeshare.excecoes;

public class SenhaNulaException extends BikeShareException {
    public SenhaNulaException() {
        super("Senha nao pode ser nula.");
    }
}
