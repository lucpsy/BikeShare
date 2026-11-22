package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando se tenta devolver uma bicicleta em uma estacao sem vagas livres.
 */
public class EstacaoCheiaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de estacao cheia.
     */
    public EstacaoCheiaException() {
        super("Estacao cheia.");
    }
}
