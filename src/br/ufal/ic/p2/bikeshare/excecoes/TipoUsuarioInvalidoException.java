package br.ufal.ic.p2.bikeshare.excecoes;

public class TipoUsuarioInvalidoException extends BikeShareException {
    public TipoUsuarioInvalidoException() {
        super("Tipo invalido.");
    }
}
