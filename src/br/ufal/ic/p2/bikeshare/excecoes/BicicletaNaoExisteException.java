package br.ufal.ic.p2.bikeshare.excecoes;

public class BicicletaNaoExisteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public BicicletaNaoExisteException() {
        super("Bicicleta nao existe.");
    }
}
