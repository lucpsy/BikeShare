package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Valor deve ser numerico.}.
 */
public class ValorNaoNumericoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Valor deve ser numerico."}.
     */
    public ValorNaoNumericoException() {
        super("Valor deve ser numerico.");
    }
}
