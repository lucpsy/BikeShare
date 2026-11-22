package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Endereco nao pode ser nulo.}.
 */
public class EnderecoNuloException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Endereco nao pode ser nulo."}.
     */
    public EnderecoNuloException() {
        super("Endereco nao pode ser nulo.");
    }
}
