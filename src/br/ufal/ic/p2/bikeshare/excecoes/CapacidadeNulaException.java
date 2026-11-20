package br.ufal.ic.p2.bikeshare.excecoes;

public class CapacidadeNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public CapacidadeNulaException() {
        super("Capacidade nao pode ser nula.");
    }
}
