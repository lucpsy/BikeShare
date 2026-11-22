package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando se tenta devolver um aluguel que nao esta mais em andamento.
 */
public class AluguelNaoEstaEmAndamentoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de aluguel nao em andamento.
     */
    public AluguelNaoEstaEmAndamentoException() {
        super("Aluguel nao esta em andamento.");
    }
}
