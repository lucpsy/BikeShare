package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Email nao pode ser nulo.}.
 */
public class EmailNuloException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Email nao pode ser nulo."}.
     */
    public EmailNuloException() {
        super("Email nao pode ser nulo.");
    }
}
