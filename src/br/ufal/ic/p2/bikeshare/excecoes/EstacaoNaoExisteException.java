package br.ufal.ic.p2.bikeshare.excecoes;

public class EstacaoNaoExisteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public EstacaoNaoExisteException() {
        super("Estacao nao existe.");
    }
}
