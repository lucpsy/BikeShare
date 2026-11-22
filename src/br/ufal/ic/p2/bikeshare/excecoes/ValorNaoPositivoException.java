package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Valor deve ser positivo.}.
 */
public class ValorNaoPositivoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Valor deve ser positivo."}.
     */
    public ValorNaoPositivoException() {
        super("Valor deve ser positivo.");
    }
}
