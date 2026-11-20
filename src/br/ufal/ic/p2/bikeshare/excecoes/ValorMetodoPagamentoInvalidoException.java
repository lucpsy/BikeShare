package br.ufal.ic.p2.bikeshare.excecoes;

public class ValorMetodoPagamentoInvalidoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public ValorMetodoPagamentoInvalidoException() {
        super("Valor de metodo de pagamento invalido.");
    }
}
