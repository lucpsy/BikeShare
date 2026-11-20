package br.ufal.ic.p2.bikeshare.excecoes;

public class BicicletaJaExisteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public BicicletaJaExisteException() {
        super("Bicicleta ja existe.");
    }
}
