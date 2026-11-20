package br.ufal.ic.p2.bikeshare.excecoes;

public class BicicletaNaoPossuiBateriaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public BicicletaNaoPossuiBateriaException() {
        super("Bicicleta nao possui bateria.");
    }
}
