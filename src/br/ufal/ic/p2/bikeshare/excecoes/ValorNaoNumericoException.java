package br.ufal.ic.p2.bikeshare.excecoes;

public class ValorNaoNumericoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public ValorNaoNumericoException() {
        super("Valor deve ser numerico.");
    }
}
