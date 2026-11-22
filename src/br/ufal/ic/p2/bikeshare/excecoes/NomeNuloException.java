package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Nome nao pode ser nulo.}.
 */
public class NomeNuloException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Nome nao pode ser nulo."}.
     */
    public NomeNuloException() {
        super("Nome nao pode ser nulo.");
    }
}
