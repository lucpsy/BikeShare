package br.ufal.ic.p2.bikeshare.excecoes;

public class UsuarioNaoUsaSaldoException extends BikeShareException {
    public UsuarioNaoUsaSaldoException() {
        super("Usuario nao usa saldo pre-carregado.");
    }
}
