package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Plano invalido.}.
 */
public class PlanoInvalidoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Plano invalido."}.
     */
    public PlanoInvalidoException() {
        super("Plano invalido.");
    }
}
