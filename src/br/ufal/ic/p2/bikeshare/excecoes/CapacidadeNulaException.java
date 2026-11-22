package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Capacidade nao pode ser nula.}.
 */
public class CapacidadeNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Capacidade nao pode ser nula."}.
     */
    public CapacidadeNulaException() {
        super("Capacidade nao pode ser nula.");
    }
}
