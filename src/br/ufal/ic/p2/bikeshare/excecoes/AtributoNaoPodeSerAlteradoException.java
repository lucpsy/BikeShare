package br.ufal.ic.p2.bikeshare.excecoes;

public class AtributoNaoPodeSerAlteradoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public AtributoNaoPodeSerAlteradoException() {
        super("Atributo nao pode ser alterado.");
    }
}
