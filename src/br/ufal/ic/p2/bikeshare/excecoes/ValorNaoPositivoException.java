package br.ufal.ic.p2.bikeshare.excecoes;

public class ValorNaoPositivoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public ValorNaoPositivoException() {
        super("Valor deve ser positivo.");
    }
}
