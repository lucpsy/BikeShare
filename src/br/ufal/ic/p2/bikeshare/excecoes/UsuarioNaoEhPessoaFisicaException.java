package br.ufal.ic.p2.bikeshare.excecoes;

public class UsuarioNaoEhPessoaFisicaException extends BikeShareException {
    public UsuarioNaoEhPessoaFisicaException() {
        super("Usuario nao eh pessoa fisica.");
    }
}
