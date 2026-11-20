package br.ufal.ic.p2.bikeshare.excecoes;

public class NivelBateriaNaoNumericoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public NivelBateriaNaoNumericoException() {
        super("Nivel de bateria deve ser numerico.");
    }
}
