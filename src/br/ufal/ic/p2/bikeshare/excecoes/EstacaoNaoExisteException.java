package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Estacao nao existe.}.
 */
public class EstacaoNaoExisteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Estacao nao existe."}.
     */
    public EstacaoNaoExisteException() {
        super("Estacao nao existe.");
    }
}
