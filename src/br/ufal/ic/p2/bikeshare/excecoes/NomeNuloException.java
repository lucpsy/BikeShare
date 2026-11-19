package br.ufal.ic.p2.bikeshare.excecoes;

public class NomeNuloException extends BikeShareException {
    public NomeNuloException() {
        super("Nome nao pode ser nulo.");
    }
}
