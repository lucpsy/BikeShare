package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Chave pix nao pode ser nula.}.
 */
public class ChavePixNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Chave pix nao pode ser nula."}.
     */
    public ChavePixNulaException() {
        super("Chave pix nao pode ser nula.");
    }
}
