package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Identificacao do usuario nao pode ser nula.}.
 */
public class IdentificacaoUsuarioNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Identificacao do usuario nao pode ser nula."}.
     */
    public IdentificacaoUsuarioNulaException() {
        super("Identificacao do usuario nao pode ser nula.");
    }
}
