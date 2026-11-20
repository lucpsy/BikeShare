package br.ufal.ic.p2.bikeshare.excecoes;

public class NivelBateriaInvalidoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public NivelBateriaInvalidoException() {
        super("Nivel de bateria deve estar entre 0 e 100.");
    }
}
