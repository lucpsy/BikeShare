package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code CPF nao pode ser nulo.}.
 */
public class CpfNuloException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "CPF nao pode ser nulo."}.
     */
    public CpfNuloException() {
        super("CPF nao pode ser nulo.");
    }
}
