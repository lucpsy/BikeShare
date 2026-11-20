package br.ufal.ic.p2.bikeshare.excecoes;

public class ValidadeCartaoNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public ValidadeCartaoNulaException() {
        super("Validade do cartao nao pode ser nula.");
    }
}
