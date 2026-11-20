package br.ufal.ic.p2.bikeshare.excecoes;

public class EnderecoNuloException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public EnderecoNuloException() {
        super("Endereco nao pode ser nulo.");
    }
}
