package br.ufal.ic.p2.bikeshare.excecoes;

public abstract class BikeShareException extends Exception {
    protected BikeShareException(String message) {
        super(message);
    }
    protected BikeShareException(String message, Throwable causa) {
        super(message,causa);
    }
}
