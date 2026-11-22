package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Classe base para todas as excecoes de dominio e persistencia do sistema BikeShare.
 */
public abstract class BikeShareException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com uma mensagem descritiva.
     *
     * @param message mensagem de erro de dominio
     */
    protected BikeShareException(String message) {
        super(message);
    }

    /**
     * Cria a excecao com uma mensagem descritiva e a causa original.
     *
     * @param message mensagem de erro de dominio
     * @param causa   excecao subjacente que originou o erro
     */
    protected BikeShareException(String message, Throwable causa) {
        super(message, causa);
    }
}
