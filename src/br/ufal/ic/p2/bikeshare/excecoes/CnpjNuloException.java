package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code CNPJ nao pode ser nulo.}.
 */
public class CnpjNuloException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "CNPJ nao pode ser nulo."}.
     */
    public CnpjNuloException() {
        super("CNPJ nao pode ser nulo.");
    }
}
