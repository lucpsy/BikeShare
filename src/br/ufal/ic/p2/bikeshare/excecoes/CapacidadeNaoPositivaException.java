package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Capacidade deve ser positiva.}.
 */
public class CapacidadeNaoPositivaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Capacidade deve ser positiva."}.
     */
    public CapacidadeNaoPositivaException() {
        super("Capacidade deve ser positiva.");
    }
}
