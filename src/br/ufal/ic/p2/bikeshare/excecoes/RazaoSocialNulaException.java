package br.ufal.ic.p2.bikeshare.excecoes;

public class RazaoSocialNulaException extends BikeShareException {
    public RazaoSocialNulaException() {
        super("Razao social nao pode ser nula.");
    }
}
