package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando uma bicicleta eletrica possui bateria abaixo do limite minimo para retirada.
 */
public class NivelBateriaInsuficienteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de nivel de bateria insuficiente.
     */
    public NivelBateriaInsuficienteException() {
        super("Nivel de bateria insuficiente.");
    }
}
