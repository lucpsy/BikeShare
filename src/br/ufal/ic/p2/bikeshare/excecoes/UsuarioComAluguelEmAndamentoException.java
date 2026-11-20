package br.ufal.ic.p2.bikeshare.excecoes;

public class UsuarioComAluguelEmAndamentoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    public UsuarioComAluguelEmAndamentoException() {
        super("Nao e possivel remover usuario com aluguel em andamento.");
    }
}
