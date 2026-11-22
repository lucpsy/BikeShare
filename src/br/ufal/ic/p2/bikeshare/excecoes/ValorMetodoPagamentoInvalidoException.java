package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Valor de metodo de pagamento invalido.}.
 */
public class ValorMetodoPagamentoInvalidoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Valor de metodo de pagamento invalido."}.
     */
    public ValorMetodoPagamentoInvalidoException() {
        super("Valor de metodo de pagamento invalido.");
    }
}
