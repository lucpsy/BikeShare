package br.ufal.ic.p2.bikeshare.excecoes;

public class CnpjNuloException extends BikeShareException {
    public CnpjNuloException() {
        super("CNPJ nao pode ser nulo.");
    }
}
