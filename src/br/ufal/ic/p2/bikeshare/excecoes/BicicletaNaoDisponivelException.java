package br.ufal.ic.p2.bikeshare.excecoes;

public class BicicletaNaoDisponivelException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public BicicletaNaoDisponivelException() {
        super("Bicicleta nao esta disponivel.");
    }
}
