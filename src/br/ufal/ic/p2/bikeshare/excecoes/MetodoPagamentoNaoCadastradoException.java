package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando um usuario sem metodo de pagamento tenta retirar uma bicicleta.
 */
public class MetodoPagamentoNaoCadastradoException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de metodo de pagamento nao cadastrado.
     */
    public MetodoPagamentoNaoCadastradoException() {
        super("Metodo de pagamento nao cadastrado.");
    }
}
