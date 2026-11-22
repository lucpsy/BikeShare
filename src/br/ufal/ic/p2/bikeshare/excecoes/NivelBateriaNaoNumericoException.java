package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Nivel de bateria deve ser numerico.}.
 */
public class NivelBateriaNaoNumericoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Nivel de bateria deve ser numerico."}.
     */
    public NivelBateriaNaoNumericoException() {
        super("Nivel de bateria deve ser numerico.");
    }
}
