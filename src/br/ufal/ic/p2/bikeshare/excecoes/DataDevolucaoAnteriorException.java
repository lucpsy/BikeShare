package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando a data de devolucao e anterior a data de retirada do aluguel.
 */
public class DataDevolucaoAnteriorException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de data de devolucao anterior a retirada.
     */
    public DataDevolucaoAnteriorException() {
        super("Data de devolucao anterior a data de retirada.");
    }
}
