package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Bicicleta ja existe.}.
 */
public class BicicletaJaExisteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Bicicleta ja existe."}.
     */
    public BicicletaJaExisteException() {
        super("Bicicleta ja existe.");
    }
}
