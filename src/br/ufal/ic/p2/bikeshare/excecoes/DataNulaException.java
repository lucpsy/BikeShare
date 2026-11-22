package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando uma data informada e nula ou vazia.
 */
public class DataNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de data nula.
     */
    public DataNulaException() {
        super("Data nao pode ser nula.");
    }
}
