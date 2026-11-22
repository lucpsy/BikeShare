package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando se tenta consultar atributos de encerramento de um aluguel ainda em andamento.
 */
public class AluguelEmAndamentoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de aluguel em andamento.
     */
    public AluguelEmAndamentoException() {
        super("Aluguel ainda esta em andamento.");
    }
}
