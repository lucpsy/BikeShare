package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Usuario nao existe.}.
 */
public class UsuarioNaoExisteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Usuario nao existe."}.
     */
    public UsuarioNaoExisteException() {
        super("Usuario nao existe.");
    }
}
