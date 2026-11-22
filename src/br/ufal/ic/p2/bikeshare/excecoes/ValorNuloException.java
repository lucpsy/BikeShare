package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Valor nao pode ser nulo.}.
 */
public class ValorNuloException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Valor nao pode ser nulo."}.
     */
    public ValorNuloException() {
        super("Valor nao pode ser nulo.");
    }
}
