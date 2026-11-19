package br.ufal.ic.p2.bikeshare.excecoes;

public class UsuarioNaoUsaPixException extends BikeShareException {
    public UsuarioNaoUsaPixException() {
        super("Usuario nao usa pix.");
    }
}
