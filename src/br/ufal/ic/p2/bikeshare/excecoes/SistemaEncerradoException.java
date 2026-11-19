package br.ufal.ic.p2.bikeshare.excecoes;

public class SistemaEncerradoException extends BikeShareException {
    public SistemaEncerradoException() {
        super("Sistema encerrado.");
    }
}
