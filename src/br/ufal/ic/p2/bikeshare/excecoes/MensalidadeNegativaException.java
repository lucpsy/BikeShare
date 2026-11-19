package br.ufal.ic.p2.bikeshare.excecoes;

public class MensalidadeNegativaException extends BikeShareException {
    public MensalidadeNegativaException() {
        super("Mensalidade deve ser nao-negativa.");
    }
}
