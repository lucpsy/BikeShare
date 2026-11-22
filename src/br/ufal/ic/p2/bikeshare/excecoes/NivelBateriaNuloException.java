package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Nivel de bateria nao pode ser nulo.}.
 */
public class NivelBateriaNuloException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Nivel de bateria nao pode ser nulo."}.
     */
    public NivelBateriaNuloException() {
        super("Nivel de bateria nao pode ser nulo.");
    }
}
