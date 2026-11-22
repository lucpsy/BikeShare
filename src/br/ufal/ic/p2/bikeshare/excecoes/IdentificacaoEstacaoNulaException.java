package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Identificacao da estacao nao pode ser nula.}.
 */
public class IdentificacaoEstacaoNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Identificacao da estacao nao pode ser nula."}.
     */
    public IdentificacaoEstacaoNulaException() {
        super("Identificacao da estacao nao pode ser nula.");
    }
}
