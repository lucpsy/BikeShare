package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Bicicleta nao possui bateria.}.
 */
public class BicicletaNaoPossuiBateriaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Bicicleta nao possui bateria."}.
     */
    public BicicletaNaoPossuiBateriaException() {
        super("Bicicleta nao possui bateria.");
    }
}
