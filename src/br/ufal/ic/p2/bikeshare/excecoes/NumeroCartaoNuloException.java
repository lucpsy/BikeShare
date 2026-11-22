package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Numero do cartao nao pode ser nulo.}.
 */
public class NumeroCartaoNuloException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Numero do cartao nao pode ser nulo."}.
     */
    public NumeroCartaoNuloException() {
        super("Numero do cartao nao pode ser nulo.");
    }
}
