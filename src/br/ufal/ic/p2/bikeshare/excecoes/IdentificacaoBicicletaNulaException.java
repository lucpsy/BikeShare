package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Identificacao da bicicleta nao pode ser nula.}.
 */
public class IdentificacaoBicicletaNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Identificacao da bicicleta nao pode ser nula."}.
     */
    public IdentificacaoBicicletaNulaException() {
        super("Identificacao da bicicleta nao pode ser nula.");
    }
}
