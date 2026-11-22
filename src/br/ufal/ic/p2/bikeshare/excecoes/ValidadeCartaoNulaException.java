package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Validade do cartao nao pode ser nula.}.
 */
public class ValidadeCartaoNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Validade do cartao nao pode ser nula."}.
     */
    public ValidadeCartaoNulaException() {
        super("Validade do cartao nao pode ser nula.");
    }
}
