package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando uma data informada possui formato ou calendario invalido.
 */
public class DataInvalidaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de data invalida.
     */
    public DataInvalidaException() {
        super("Data invalida.");
    }
}
