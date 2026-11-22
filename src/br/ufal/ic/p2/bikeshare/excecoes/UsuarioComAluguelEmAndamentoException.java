package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao de dominio lancada para a condicao: {@code Nao e possivel remover usuario com aluguel em andamento.}.
 */
public class UsuarioComAluguelEmAndamentoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao {@code "Nao e possivel remover usuario com aluguel em andamento."}.
     */
    public UsuarioComAluguelEmAndamentoException() {
        super("Nao e possivel remover usuario com aluguel em andamento.");
    }
}
