package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Atributo nao existe.}.
 */
public class AtributoNaoExisteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Atributo nao existe."}.
     */
    public AtributoNaoExisteException() {
        super("Atributo nao existe.");
    }
}
