package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Mensalidade nao pode ser nula.}.
 */
public class MensalidadeNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Mensalidade nao pode ser nula."}.
     */
    public MensalidadeNulaException() {
        super("Mensalidade nao pode ser nula.");
    }
}
