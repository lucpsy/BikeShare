package br.ufal.ic.p2.bikeshare.excecoes;

public class CpfNuloException extends BikeShareException {
    public CpfNuloException() {
        super("CPF nao pode ser nulo.");
    }
}
