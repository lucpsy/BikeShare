package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Senha nao pode ser nula.}.
 */
public class SenhaNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Senha nao pode ser nula."}.
     */
    public SenhaNulaException() {
        super("Senha nao pode ser nula.");
    }
}
