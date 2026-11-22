package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Bicicleta nao existe.}.
 */
public class BicicletaNaoExisteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Bicicleta nao existe."}.
     */
    public BicicletaNaoExisteException() {
        super("Bicicleta nao existe.");
    }
}
