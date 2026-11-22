package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Tipo invalido.}.
 */
public class TipoBicicletaInvalidoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Tipo invalido."}.
     */
    public TipoBicicletaInvalidoException() {
        super("Tipo invalido.");
    }
}
