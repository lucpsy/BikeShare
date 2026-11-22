package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando um aluguel consultado ou manipulado nao existe no sistema.
 */
public class AluguelNaoExisteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de aluguel inexistente.
     */
    public AluguelNaoExisteException() {
        super("Aluguel nao existe.");
    }
}
