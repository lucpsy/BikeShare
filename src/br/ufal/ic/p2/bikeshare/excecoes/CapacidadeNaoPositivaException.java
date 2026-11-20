package br.ufal.ic.p2.bikeshare.excecoes;

public class CapacidadeNaoPositivaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public CapacidadeNaoPositivaException() {
        super("Capacidade deve ser positiva.");
    }
}
