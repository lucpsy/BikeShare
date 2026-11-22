package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Mensalidade deve ser numerica.}.
 */
public class MensalidadeNaoNumericaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Mensalidade deve ser numerica."}.
     */
    public MensalidadeNaoNumericaException() {
        super("Mensalidade deve ser numerica.");
    }
}
