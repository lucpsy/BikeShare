package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Plano nao possui mensalidade.}.
 */
public class PlanoNaoPossuiMensalidadeException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Plano nao possui mensalidade."}.
     */
    public PlanoNaoPossuiMensalidadeException() {
        super("Plano nao possui mensalidade.");
    }
}
