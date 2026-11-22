package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Tipo invalido.}.
 */
public class TipoUsuarioInvalidoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Tipo invalido."}.
     */
    public TipoUsuarioInvalidoException() {
        super("Tipo invalido.");
    }
}
