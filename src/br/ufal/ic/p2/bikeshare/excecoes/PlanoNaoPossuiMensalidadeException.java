package br.ufal.ic.p2.bikeshare.excecoes;

public class PlanoNaoPossuiMensalidadeException extends BikeShareException {
    public PlanoNaoPossuiMensalidadeException() {
        super("Plano nao possui mensalidade.");
    }
}
