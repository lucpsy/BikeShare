package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Nivel de bateria deve estar entre 0 e 100.}.
 */
public class NivelBateriaInvalidoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Nivel de bateria deve estar entre 0 e 100."}.
     */
    public NivelBateriaInvalidoException() {
        super("Nivel de bateria deve estar entre 0 e 100.");
    }
}
