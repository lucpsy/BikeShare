package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando o saldo pre-carregado do usuario e insuficiente para retirada ou pagamento.
 */
public class SaldoInsuficienteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de saldo insuficiente.
     */
    public SaldoInsuficienteException() {
        super("Saldo insuficiente.");
    }
}
