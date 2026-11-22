package br.ufal.ic.p2.bikeshare.excecoes;

/**
 * Excecao lancada quando um usuario inadimplente tenta retirar uma bicicleta.
 */
public class UsuarioInadimplenteException extends BikeShareException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem padrao de usuario inadimplente.
     */
    public UsuarioInadimplenteException() {
        super("Usuario inadimplente.");
    }
}
