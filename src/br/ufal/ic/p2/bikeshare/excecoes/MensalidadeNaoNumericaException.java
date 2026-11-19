package br.ufal.ic.p2.bikeshare.excecoes;

public class MensalidadeNaoNumericaException extends BikeShareException {
    public MensalidadeNaoNumericaException() {
        super("Mensalidade deve ser numerica.");
    }
}
