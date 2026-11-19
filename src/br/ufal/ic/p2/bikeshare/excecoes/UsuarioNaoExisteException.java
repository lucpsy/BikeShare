package br.ufal.ic.p2.bikeshare.excecoes;

public class UsuarioNaoExisteException extends BikeShareException {
    public UsuarioNaoExisteException() {
        super("Usuario nao existe.");
    }
}
