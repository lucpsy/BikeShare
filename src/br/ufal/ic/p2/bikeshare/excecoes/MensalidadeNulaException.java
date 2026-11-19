package br.ufal.ic.p2.bikeshare.excecoes;

public class MensalidadeNulaException extends BikeShareException {
    public MensalidadeNulaException() {
        super("Mensalidade nao pode ser nula.");
    }
}
