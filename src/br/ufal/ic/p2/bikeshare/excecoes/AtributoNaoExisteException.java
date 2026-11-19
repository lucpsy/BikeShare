package br.ufal.ic.p2.bikeshare.excecoes;

public class AtributoNaoExisteException extends BikeShareException {
    public AtributoNaoExisteException() {
        super("Atributo nao existe.");
    }
}
