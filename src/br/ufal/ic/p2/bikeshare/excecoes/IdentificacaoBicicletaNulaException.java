package br.ufal.ic.p2.bikeshare.excecoes;

public class IdentificacaoBicicletaNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public IdentificacaoBicicletaNulaException() {
        super("Identificacao da bicicleta nao pode ser nula.");
    }
}
