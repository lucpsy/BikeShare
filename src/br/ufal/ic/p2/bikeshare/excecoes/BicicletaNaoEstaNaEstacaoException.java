package br.ufal.ic.p2.bikeshare.excecoes;

public class BicicletaNaoEstaNaEstacaoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public BicicletaNaoEstaNaEstacaoException() {
        super("Bicicleta nao esta nesta estacao.");
    }
}
