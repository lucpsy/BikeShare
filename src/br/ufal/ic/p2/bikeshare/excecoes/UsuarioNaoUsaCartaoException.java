package br.ufal.ic.p2.bikeshare.excecoes;

public class UsuarioNaoUsaCartaoException extends BikeShareException {
    public UsuarioNaoUsaCartaoException() {
        super("Usuario nao usa cartao.");
    }
}
