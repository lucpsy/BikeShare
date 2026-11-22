package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Razao social nao pode ser nula.}.
 */
public class RazaoSocialNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Razao social nao pode ser nula."}.
     */
    public RazaoSocialNulaException() {
        super("Razao social nao pode ser nula.");
    }
}
