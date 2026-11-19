package br.ufal.ic.p2.bikeshare.excecoes;

public class UsuarioNaoEhPessoaJuridicaException extends BikeShareException {
    public UsuarioNaoEhPessoaJuridicaException() {
        super("Usuario nao eh pessoa juridica.");
    }
}
