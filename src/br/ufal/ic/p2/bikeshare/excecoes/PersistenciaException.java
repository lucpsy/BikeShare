package br.ufal.ic.p2.bikeshare.excecoes;

public class PersistenciaException extends BikeShareException {
    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
