package br.ufal.ic.p2.bikeshare.excecoes;

public class EmailJaCadastradoException extends BikeShareException {
    public EmailJaCadastradoException() {
        super("Email ja cadastrado.");
    }
}
