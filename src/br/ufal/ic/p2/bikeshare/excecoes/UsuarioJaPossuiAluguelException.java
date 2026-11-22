package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando um usuario que ja possui aluguel ativo tenta retirar outra bicicleta.
 */
public class UsuarioJaPossuiAluguelException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de usuario com aluguel em andamento.
     */
    public UsuarioJaPossuiAluguelException() {
        super("Usuario ja possui um aluguel em andamento.");
    }
}
