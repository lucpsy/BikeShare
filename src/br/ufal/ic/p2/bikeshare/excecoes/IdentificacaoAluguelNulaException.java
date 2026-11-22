package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando a identificacao de um aluguel e nula ou vazia.
 */
public class IdentificacaoAluguelNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de identificacao de aluguel nula.
     */
    public IdentificacaoAluguelNulaException() {
        super("Identificacao do aluguel nao pode ser nula.");
    }
}
