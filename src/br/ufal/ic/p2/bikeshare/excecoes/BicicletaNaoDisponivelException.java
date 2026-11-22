package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Bicicleta nao esta disponivel.}.
 */
public class BicicletaNaoDisponivelException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Bicicleta nao esta disponivel."}.
     */
    public BicicletaNaoDisponivelException() {
        super("Bicicleta nao esta disponivel.");
    }
}
