package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Email ja cadastrado.}.
 */
public class EmailJaCadastradoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Email ja cadastrado."}.
     */
    public EmailJaCadastradoException() {
        super("Email ja cadastrado.");
    }
}
