package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Usuario nao usa cartao.}.
 */
public class UsuarioNaoUsaCartaoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Usuario nao usa cartao."}.
     */
    public UsuarioNaoUsaCartaoException() {
        super("Usuario nao usa cartao.");
    }
}
