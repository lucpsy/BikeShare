package br.ufal.ic.p2.bikeshare.excecoes;

public class ChavePixNulaException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public ChavePixNulaException() {
        super("Chave pix nao pode ser nula.");
    }
}
