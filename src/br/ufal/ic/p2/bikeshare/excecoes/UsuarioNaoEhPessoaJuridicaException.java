package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Usuario nao eh pessoa juridica.}.
 */
public class UsuarioNaoEhPessoaJuridicaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Usuario nao eh pessoa juridica."}.
     */
    public UsuarioNaoEhPessoaJuridicaException() {
        super("Usuario nao eh pessoa juridica.");
    }
}
