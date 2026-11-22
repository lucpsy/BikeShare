package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Estacao sem vagas disponiveis.}.
 */
public class EstacaoSemVagasException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Estacao sem vagas disponiveis."}.
     */
    public EstacaoSemVagasException() {
        super("Estacao sem vagas disponiveis.");
    }
}
