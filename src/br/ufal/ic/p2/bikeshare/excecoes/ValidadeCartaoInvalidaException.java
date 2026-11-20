package br.ufal.ic.p2.bikeshare.excecoes;

public class ValidadeCartaoInvalidaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public ValidadeCartaoInvalidaException() {
        super("Validade do cartao invalida.");
    }
}
