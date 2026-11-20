package br.ufal.ic.p2.bikeshare.excecoes;

public class TipoBicicletaInvalidoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public TipoBicicletaInvalidoException() {
        super("Tipo invalido.");
    }
}
