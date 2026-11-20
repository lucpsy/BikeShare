package br.ufal.ic.p2.bikeshare.excecoes;

public class CapacidadeNaoNumericaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public CapacidadeNaoNumericaException() {
        super("Capacidade deve ser numerica.");
    }
}
