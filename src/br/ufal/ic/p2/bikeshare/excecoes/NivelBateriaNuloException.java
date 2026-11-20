package br.ufal.ic.p2.bikeshare.excecoes;

public class NivelBateriaNuloException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public NivelBateriaNuloException() {
        super("Nivel de bateria nao pode ser nulo.");
    }
}
